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
import com.shared.model.gameboard.Tile;

/**
 * Server-side implementation of RPC framework. Keeps track of which players are
 * connected at any given time and synchronizes game state among all of them.
 */
@SuppressWarnings("serial")
public class SimpleSimulatorImpl extends RemoteServiceServlet implements
		SimpleSimulator {

	Login login = new Login();
	Controller controller;
	int currentTurn;
	boolean debug = false;
	
	private Map<Integer, PlayerState> playerTable = new ConcurrentHashMap<Integer, PlayerState>();
	private Map<Integer, PlayerState> newPlayerTable = new ConcurrentHashMap<Integer, PlayerState>();

	private long lastReadyTime = System.currentTimeMillis();
	private int timeout = 100000; // Number of milliseconds to wait before
									// dropping connections
	int nextPlayerSlot = 0;
	
	public static enum PlayerState {
		ModelUpToDate,
		HasSentCommands
	}
	
	@Override
	public void init() {
		controller = new Controller();
		controller.run();
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				advanceTable();
//			}
//		}, 0, 100);
	}
	
	public CommandPacket sendCommands( int playerNumber, Queue<Command> commandQueue ) {

		// Are you still part of the game, or have you timed out?
		if (!playerTable.containsKey(playerNumber)) {
			return null;
		}

		controller.sendCommands( commandQueue );
		playerTable.put(playerNumber, PlayerState.HasSentCommands);
		synchronizeOn(PlayerState.HasSentCommands);
		
		awaitPacketReady();
		return controller.getPacketToSend();
	}

	private void awaitPacketReady() {
		while (!controller.isPacketReady()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String startSimulation() {
		return null;
	}

	/**
	 * Confirm that the most recent simulation state was received, prevents
	 * simulation from getting more than one turn ahead of the clients.
	 */
	@Override

	public String confirmReceipt( int playerNumber, int turnNumber ) {
		
		playerTable.put(playerNumber, PlayerState.ModelUpToDate);
		if(debug) System.out.println(">>> Player " + playerNumber + " confirms receipt of turn " + turnNumber);

		synchronizeOn(PlayerState.ModelUpToDate);
		return null;
	}

	/**
	 * getGame - get the current GameModel, should only be used when the player
	 * first joins the server. Add connect to map of current connections.
	 */
	@Override
	public synchronized GameModel getGame( int playerNumber, int lastTurnReceived ) {
		
		System.out.println("    GETGAME: Player " + playerNumber + " waiting for server to start receiving commands");
		if (playerTable.size() == 0) {
			playerTable.put(playerNumber, PlayerState.HasSentCommands);
		} else {
			newPlayerTable.put(playerNumber, PlayerState.HasSentCommands);
		}
		
		awaitPacketReady();
		System.out.println("    GETGAME: Player " + playerNumber + " about to receive game!");
		return controller.getGameModel();
	}

	public synchronized Integer joinGame() {
		System.out.println("New player joining the game!");
		return nextPlayerSlot++;
	}

	public Integer exitGame(int playerNumber) {
		System.out.println("Player " + playerNumber + " exiting the game!");
		playerTable.remove(playerNumber);
		advanceTable();
		return nextPlayerSlot;
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
	
	public void synchronizeOn( PlayerState state ) {
		synchronized(this) {
			if(allPlayersAtState(state)) {
				if( state == PlayerState.HasSentCommands ) {
					for(Entry<Integer,PlayerState> e : newPlayerTable.entrySet()) {
						playerTable.put(e.getKey(), e.getValue());
					}
					newPlayerTable.clear();
					controller.continueSimulation();
				}
				notifyAll();
			} else {
				try {
					wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void advanceTable() {
		synchronized(this) {
			if(playerTable.isEmpty()) {
				notifyAll();
				return;
			} else if(allPlayersAtState(PlayerState.HasSentCommands)) {
				for(Entry<Integer,PlayerState> e : newPlayerTable.entrySet()) {
					playerTable.put(e.getKey(), e.getValue());
				}
				newPlayerTable.clear();
				controller.continueSimulation();
				notifyAll();
			} else if(allPlayersAtState(PlayerState.ModelUpToDate)) {
				notifyAll();	
			}
		}
	}

	@Override
	public boolean login(String username) {
		return login.addUserToGame(username, "1");
	}
	
	public Tile[][] getTiles() {
		return controller.getGameModel().getBoard().getTiles();
	}
	
}
