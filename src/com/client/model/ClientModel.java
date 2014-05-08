package com.client.model;

import java.util.LinkedList;
import java.util.Queue;

import com.client.SimpleSimulator;
import com.client.SimpleSimulatorAsync;
import com.client.gameinterface.Console;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.model.commands.Command;
import com.shared.model.control.CommandPacket;
import com.shared.model.control.GameModel;

public class ClientModel {
	
	private int turnNumber = -1;
	private int playerNumber = -1;
	float[] position = { 0.0F, 0.0F };
	private long lastUpdateTime;
	private final SimpleSimulatorAsync simpleSimulator;
	private GameModel model = new GameModel();
	private boolean readyForNext = true;
	private int cycleTime = 100;
	private boolean debug = false;
	
	private Queue<Command> commandQueue = new LinkedList<Command>();
	
	public ClientModel() {
		
		simpleSimulator = GWT.create(SimpleSimulator.class);
		
		Window.addWindowClosingHandler(new Window.ClosingHandler() {
			
			@Override
			public void onWindowClosing(ClosingEvent event) {
				simpleSimulator.exitGame(playerNumber, null);

			}
		});

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
				if(debug) Console.log("Joined game");
				playerNumber = playerNum;
				retrieveGame();
			}

		});
		
	}
	
	private void retrieveGame() {
		simpleSimulator.getGame(playerNumber, -1, new AsyncCallback<GameModel>() {

			@Override
			public void onFailure(Throwable caught) {
				if(debug) Console.log("Could not retrieve game.");
			}

			@Override
			public void onSuccess(GameModel result) {
				if(debug) Console.log("Game retrieved!");
				model = result;
				beginPlaying();
			}
			
		});
	}
	
	public void beginPlaying(){
		readyForNext = true;
		
		Timer pollTimer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(!readyForNext) {
					//Console.log("Not ready for next set of commands");
					return;
				}
				
				readyForNext = false;
				if(debug) Console.log("Requesting simulation state...");
				if(debug) Console.log("Attempting to send " + commandQueue.size() + " commands to server");
				
				simpleSimulator.sendCommands(playerNumber, commandQueue, new AsyncCallback<CommandPacket>() {
					@Override
					public void onFailure(Throwable caught) {
						if(debug) Console.log("    Unable to receive simulation state");
						if(debug) Console.log(caught.getMessage());
						readyForNext = true;
					}

					@Override
					public void onSuccess(CommandPacket result) {
						
						commandQueue = new LinkedList<Command>();
						
						if(result == null) {
							simpleSimulator.getGame(playerNumber, -1, new AsyncCallback<GameModel>() {

								@Override
								public void onFailure(Throwable caught) {
									if(debug) Console.log("Could not retrieve game.");
								}

								@Override
								public void onSuccess(GameModel result) {
									if(debug) Console.log("Game retrieved!");
									model = result;
								}
								
							});
						} else {
							if(debug) Console.log(result.getCommandQueue().size() + " commands received, simulate for " + result.getTime() + " ms");
							result.executeOn(model);
							model.advanceTimeStep(result.getTime());
							
							long currTime = System.currentTimeMillis();
							cycleTime = (int) (currTime - lastUpdateTime);
							lastUpdateTime = currTime;
							turnNumber = result.getTurnNumber();
							if(debug) Console.log("    Simulation state received! Cycle time: " + cycleTime + " ms");
							confirmReceipt();
						}
					}
				});
			}

		};

		pollTimer.scheduleRepeating(20);
		
	}
	
	public void confirmReceipt() {
		simpleSimulator.confirmReceipt(playerNumber, turnNumber, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				if(debug) Console.log("    Receipt failed, retrying...");
			}

			@Override
			public void onSuccess(String result) {
				if(debug) Console.log("    Receipt success!");
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
