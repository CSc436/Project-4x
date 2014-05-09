package com.server;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

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
	
	Map<Integer, PlayerState> playerTable = new ConcurrentHashMap<Integer, PlayerState>();
	private long lastReadyTime = System.currentTimeMillis();
	private int timeout = 100000; // Number of milliseconds to wait before dropping connections
	int nextPlayerSlot = 0;
	
	public static enum PlayerState {
		AwaitConfirmation,
		ModelUpToDate,
		NoCommandsSent,
		HasSentCommands
	}
	
	@Override
	public void init() {
		controller = new Controller();
		controller.run();
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				updateServerState();
			}
		};
		
		timer.scheduleAtFixedRate(task, 0, 10);
	}
	
	public CommandPacket sendCommands( int playerNumber, Queue<Command> commandQueue ) {
		// Are you still part of the game, or have you timed out?
		if( !playerTable.containsKey(playerNumber) ) {
			return null;
		}
		
		System.out.println("    Player " + playerNumber + " waiting for server to start receiving commands");
		synchronizeOn(PlayerState.NoCommandsSent, PlayerState.HasSentCommands);
		
		System.out.println("    Player " + playerNumber + " sending its commands!");
		controller.sendCommands( commandQueue );
		playerTable.put(playerNumber, PlayerState.HasSentCommands);
		
		while(!controller.isPacketReady() ) {
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
		
		System.out.println("    Player " + playerNumber + " waiting for all players to confirm receipt");
		synchronizeOn(PlayerState.AwaitConfirmation, PlayerState.ModelUpToDate);
		
		System.out.println("    Player " + playerNumber + " done waiting for confirmReceipt!");
		
		if(turnNumber <= controller.turnNumber) {
			playerTable.put(playerNumber, PlayerState.ModelUpToDate);
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
		
		System.out.println("    GETGAME: Player " + playerNumber + " waiting for server to start receiving commands");
		synchronizeOn(PlayerState.NoCommandsSent, PlayerState.HasSentCommands);
		
		System.out.println("    GETGAME: Player " + playerNumber + " putting itself in the table!");
		
		synchronized(playerTable) {
			playerTable.put(playerNumber, PlayerState.HasSentCommands);
		}
		
		while(!controller.isPacketReady() ) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("    GETGAME: Player " + playerNumber + " about to receive game!");
		return controller.getGameModel();
	}
	
	public Integer joinGame() {
		if(!controller.isRunning) controller.run();
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
	public synchronized void updateServerState() {
		long currTime = System.currentTimeMillis();
		/*
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
		} else {*/
			synchronized(playerTable) {
				if(allPlayersAtState(PlayerState.ModelUpToDate)) {
					System.out.println("Ready to receive commands from all players on turn " + controller.getGameModel().getTurnNumber());
					lastReadyTime = currTime;
					setAllPlayerStates(PlayerState.NoCommandsSent);
				}
				else if(allPlayersAtState(PlayerState.HasSentCommands)) {
					System.out.println("Ready to send command packet to all players on turn " + controller.getGameModel().getTurnNumber());
					controller.continueSimulation();
					setAllPlayerStates(PlayerState.AwaitConfirmation);
				} else {
					//System.out.println("WTF: " + controller.getGameModel().getTurnNumber());
				}
			}
		//}
		
	}
	
	public boolean allPlayersAtState(PlayerState...states) {
		synchronized(playerTable) {
			Set<Integer> keySet = playerTable.keySet();
			for( Integer key : keySet ) {
				boolean playerstate = false;
				for(PlayerState state: states) {
					playerstate |= playerTable.get(key) == state;
				}
				if(!playerstate) {
					if(debug) System.out.println(">>> Still waiting for player " + key);
					return false;
				}
			}
			return true;
		}
	}
	
	public void setAllPlayerStates(PlayerState state) {
		synchronized(playerTable) {
			Set<Integer> keySet = playerTable.keySet();
			for( Integer key : keySet ) {
				playerTable.put(key, state);
			}
		}
	}
	
	public void synchronizeOn(PlayerState...states) {
		while(!allPlayersAtState(states)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
