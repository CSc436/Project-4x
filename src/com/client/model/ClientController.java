package com.client.model;

import static com.google.gwt.query.client.GQuery.$;
import java.util.LinkedList;
import java.util.Queue;

import com.client.SimpleSimulator;
import com.client.SimpleSimulatorAsync;
import com.client.gameinterface.Console;
import com.client.gameinterface.GameInterface;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.model.commands.Command;
import com.shared.model.control.CommandPacket;
import com.shared.model.control.GameModel;

public class ClientController {
	
	private int turnNumber = -1;
	private int playerNumber = -1;
	float[] position = { 0.0F, 0.0F };
	private long lastUpdateTime;
	private final SimpleSimulatorAsync simpleSimulator;
	private GameModel model = new GameModel();
	private volatile boolean readyForNext = false;
	private int cycleTime = 100;
	
	private Queue<Command> commandQueue = new LinkedList<Command>();
	private Queue<Command> commandQueueCopy = new LinkedList<Command>();
	private boolean debug = false;
	
	public ClientController() {
		
		simpleSimulator = GWT.create(SimpleSimulator.class);
		
		Window.addWindowClosingHandler(new Window.ClosingHandler() {
			
			@Override
			public void onWindowClosing(ClosingEvent event) {
				simpleSimulator.exitGame(playerNumber, null);

			}
		});

	}
	
	public int getPlayerID() {
		return playerNumber;
	}
	
	public void sendCommand( Command c ) {
		commandQueue.add(c);
	}
	
	public void run() {
		
		simpleSimulator.joinGame(new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				if(debug) Console.log("Failed to join simulation");
			}

			@Override
			public void onSuccess(Integer playerNum) {
				Console.log("Joined game");
				playerNumber = playerNum;
				retrieveGame();
			}

		});
		
	}
	
	private void retrieveGame() {
		simpleSimulator.getGame(playerNumber, -1, new AsyncCallback<GameModel>() {

			@Override
			public void onFailure(Throwable caught) {
				Console.log("Could not retrieve game.");
			}

			@Override
			public void onSuccess(GameModel result) {
				if(debug) Console.log("Game retrieved!");
				model = result;
				GameInterface.setGameModel(result);
				$("#loading-screen").remove();
				turnNumber = result.getTurnNumber();
				simpleSimulator.confirmReceipt(playerNumber, turnNumber, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Console.log("    Receipt failed, retrying...");
					}

					@Override
					public void onSuccess(String result) {
						if(debug) Console.log("    Receipt success!");
						readyForNext = true;
						beginPlaying();
					}
				});
			}
			
		});
	}
	
	private Timer pollTimer;
	
	public void beginPlaying(){
		
		pollTimer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(!readyForNext) {
					//Console.log("Not ready for next set of commands");
					resetTimer();
					return;
				}
				
				readyForNext = false;
				if(debug) Console.log("Requesting simulation state...");
				if(debug) Console.log("Attempting to send " + commandQueue.size() + " commands to server");
				
				// Create a copy so we know exactly which commands are being sent
				// This way we don't lose any command that get added when the async method
				//		returns and clears everything out
				for (Command c : commandQueue) {
					commandQueueCopy.add(c);
				}
				
				simpleSimulator.sendCommands(playerNumber, commandQueue, new AsyncCallback<CommandPacket>() {
					@Override
					public void onFailure(Throwable caught) {
						Console.log("    Unable to receive simulation state");
						readyForNext = true;
						resetTimer();
					}

					@Override
					public void onSuccess(CommandPacket result) {
						
						for (Command c: commandQueueCopy) {
							// Remove every item in the copy from commandQueue
							commandQueue.remove(c);
						}
						// Reset commandQueueCopy
						commandQueueCopy = new LinkedList<Command>();
						
						if(result == null) {
							simpleSimulator.getGame(playerNumber, -1, new AsyncCallback<GameModel>() {

								@Override
								public void onFailure(Throwable caught) {
									Console.log("Could not retrieve game.");
								}

								@Override
								public void onSuccess(GameModel result) {
									if(debug) Console.log("Game retrieved!");
									model = result;
								}
								
							});
						} else {
							Console.log("Turn " + result.getTurnNumber() + ", " + result.getCommandQueue().size() + " commands received, simulate for " + result.getTime() + " ms");
							result.executeOn(model);
							model.advanceTimeStep(result.getTime());
							
							long currTime = System.currentTimeMillis();
							cycleTime = (int) (currTime - lastUpdateTime);
							lastUpdateTime = currTime;
							turnNumber = result.getTurnNumber();
							if(debug) Console.log("    Simulation state received! Cycle time: " + cycleTime + " ms");
							confirmReceipt();
						}
						resetTimer();
					}
				});
			}

		};

		//pollTimer.scheduleRepeating(20);
		pollTimer.schedule(20);
		
	}
	
	/**
	 * Resets the Timer in the beginPlaying method
	 * This method is called when the SimpleSimulator Async method 'returns' (callsback?)
	 * Also if 'readyForNext' is false
	 */
	private void resetTimer() {
		pollTimer.schedule(20);
	}
	
	public void confirmReceipt() {
		simpleSimulator.confirmReceipt(playerNumber, turnNumber, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Console.log("    Receipt failed, retrying...");
			}

			@Override
			public void onSuccess(String result) {
				Console.log("    Receipt success!");
				readyForNext = true;
			}

		});
	}
	
	public GameModel getGameModel() {
		return model;
	}
	
	public int timeSinceLastUpdate() {
		return (int) (System.currentTimeMillis() - lastUpdateTime);
	}
	
}
