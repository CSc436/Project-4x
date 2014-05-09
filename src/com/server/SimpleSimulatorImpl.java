package com.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.client.SimpleSimulator;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.shared.model.commands.Command;
import com.shared.model.control.CommandPacket;
import com.shared.model.control.GameModel;

/**
 * Server-side implementation of RPC framework. Keeps track of which players are connected
 * at any given time and synchronizes game state among all of them.
 */
@SuppressWarnings("serial")
public class SimpleSimulatorImpl extends RemoteServiceServlet implements SimpleSimulator {
	
	Controller controller;
	int currentTurn;
	boolean debug = false;
	
	HashMap<Integer, Boolean> playerTable = new HashMap<Integer, Boolean>();
	private long lastReadyTime = System.currentTimeMillis();
	private int timeout = 100000; // Number of milliseconds to wait before dropping connections
	int nextPlayerSlot = 0;
	
	
	@Override
	public void init() {
		controller = new Controller();
		controller.run();
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				trySendGame();
			}
		};
		
		timer.scheduleAtFixedRate(task, 0, 10);
	}
	
	
	public CommandPacket sendCommands( int playerNumber, Queue<Command> commandQueue ) {
		// Are you still part of the game, or have you timed out?
		if( !playerTable.containsKey(playerNumber) ) {
			return null;
		}
		controller.sendCommands( commandQueue );
		
		while(!controller.isPacketReady()) {
			//System.out.println("    Client already up to date");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return controller.getPacketToSend();
	}

	@Override
	public String startSimulation() {
		return null;
	}
	
	/**
	 *  Confirm that the most recent simulation state was received, prevents simulation from
	 *  getting more than one turn ahead of the clients.
	 */
	@Override
	public String confirmReceipt( int playerNumber, int turnNumber ) {
		if(turnNumber <= controller.turnNumber) {
			playerTable.put(playerNumber, true);
			if(debug) System.out.println(">>> Player " + playerNumber + " confirms receipt of turn " + turnNumber);
		}
		return null;
	}

	/**
	 * getGame - get the current GameModel, should only be used when the player first joins
	 * the server. Add connect to map of current connections.
	 */
	@Override
	public GameModel getGame( int playerNumber, int lastTurnReceived ) {
		playerTable.put(playerNumber, false);
		return controller.getGameModel();
	}
	
	public Integer joinGame() {
		if(!controller.isRunning) controller.run();
		playerTable.put(nextPlayerSlot, false);
		return nextPlayerSlot++;
	}
	
	public Integer exitGame( int playerNumber ) {
		playerTable.remove(playerNumber);
		if(playerTable.isEmpty())
			controller.stop();
		return nextPlayerSlot;
	}
	
	/**
	 * Tries to send the next set of commands out to players if they are all ready.
	 * @return - whether or not every player is ready to continue with the game
	 */
	public synchronized boolean trySendGame() {
		Set<Integer> keySet = playerTable.keySet();
		long currTime = System.currentTimeMillis();
		
		if(currTime > lastReadyTime + timeout) {
			LinkedList<Integer> dropList = new LinkedList<Integer>();
			for( Integer key : keySet ) {
				if(!playerTable.get(key)) {
					if(debug) System.out.println("!!! Dropping player " + key);
					dropList.add(key);
				}
			}
			for( Integer i : dropList )
				playerTable.remove(i);
		} else {
			for( Integer key : keySet ) {
				if(!playerTable.get(key)) {
					if(debug) System.out.println(">>> Still waiting for player " + key);
					return false;
				}
			}
		}
		
		keySet = playerTable.keySet();
		for( Integer key : keySet ) {
			playerTable.put(key, false);
		}
		controller.continueSimulation();
		lastReadyTime = currTime;
		return true;
	}

}
