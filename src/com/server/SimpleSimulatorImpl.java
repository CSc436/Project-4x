package com.server;

import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

import com.client.SimpleSimulator;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.shared.model.commands.Command;
import com.shared.model.control.Controller;
import com.shared.model.control.GameModel;


/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SimpleSimulatorImpl extends RemoteServiceServlet implements
		SimpleSimulator {
	
	Controller controller = new Controller();
	int currentTurn;
	boolean debug = false;
	
	HashMap<Integer, Boolean> playerTable = new HashMap<Integer, Boolean>();
	
	int nextPlayerSlot = 0;

	public void sendCommand(Command input) throws IllegalArgumentException {
		
		controller.queueCommand(input);

	}
	
	public GameModel sendCommands( Queue<Command> commandQueue ) {
		/*
		while( !requestQueue.isEmpty() ) {
			Request r = requestQueue.remove();
			r.executeOn(m);
		}
		*/
		controller.simulateFrame();
		return controller.getGameModel();
	}

	@Override
	public String startSimulation() {
		
		if(!controller.isRunning)
			controller.run();
		
		return null;
	}
	
	// Confirm that the most recent simulation state was received, prevents
	@Override
	public String confirmReceipt( int playerNumber, int turnNumber ) {
		if(turnNumber <= controller.turnNumber) {
			playerTable.put(playerNumber, true);
			if(debug) System.out.println(">>> Player " + playerNumber + " confirms receipt of turn " + turnNumber);
		}
		
		Set<Integer> keySet = playerTable.keySet();
		for( Integer key : keySet ) {
			if(!playerTable.get(key)) {
				if(debug) System.out.println(">>> Still waiting for player " + key);
				return null;
			}
		}
		
		controller.continueSimulation();
		for( Integer key : keySet ) {
			playerTable.put(key, false);
		}
		if(debug) System.out.println(">>> All players received model on turn " + turnNumber);
		return null;
	}

	@Override

	public GameModel getSimulationState( int playerNumber, int lastTurnReceived ) {
		while(!controller.sendingGame()) {
			//System.out.println("    Client already up to date");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//playerTable.put(playerNumber, true);
		return controller.getGameModel();
	}
	
	public Integer joinSimulation() {
		if(!controller.isRunning) controller.run();
		playerTable.put(nextPlayerSlot, true);
		return nextPlayerSlot++;
	}
	
	public Integer exitGame( int playerNumber ) {
		playerTable.remove(playerNumber);
		if(playerTable.isEmpty())
			controller.stop();
		return nextPlayerSlot;

	}

}
