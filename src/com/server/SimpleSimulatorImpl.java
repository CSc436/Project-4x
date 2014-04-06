package com.server;

import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import com.client.SimpleSimulator;
import com.shared.Request;
import com.shared.SimpleGameModel;

import de.csenk.gwt.ws.server.jetty.JettyWebSocketConnection;
import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.Handler;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SimpleSimulatorImpl extends WebSocketServlet implements
		SimpleSimulator {
	
	ModelController m = new ModelController();
	int currentTurn;
	boolean debug = false;
	
	HashMap<Integer, Boolean> playerTable = new HashMap<Integer, Boolean>();
	
	int nextPlayerSlot = 0;

	public Request[] sendRequest(Request input) throws IllegalArgumentException {
		
		m.queueRequest(input);

		return new Request[] {input};
	}
	
	public SimpleGameModel sendRequests( Queue<Request> requestQueue ) {
		/*
		while( !requestQueue.isEmpty() ) {
			Request r = requestQueue.remove();
			r.executeOn(m);
		}
		*/
		m.simulateFrame();
		return m.getGame();
	}

	@Override
	public String startSimulation() {
		
		if(!m.isRunning)
			m.run();
		
		return null;
	}
	
	// Confirm that the most recent simulation state was received, prevents
	@Override
	public String confirmReceipt( int playerNumber, int turnNumber ) {
		if(turnNumber <= m.turnNumber) {
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
		
		m.continueSimulation();
		for( Integer key : keySet ) {
			playerTable.put(key, false);
		}
		if(debug) System.out.println(">>> All players received model on turn " + turnNumber);
		return null;
	}

	@Override

	public SimpleGameModel getSimulationState( int playerNumber, int lastTurnReceived ) {
		while(!m.sendingGame()) {
			//System.out.println("    Client already up to date");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//playerTable.put(playerNumber, true);
		return m.getGame();
	}
	
	public Integer joinSimulation() {
		if(!m.isRunning) m.run();
		playerTable.put(nextPlayerSlot, true);
		return nextPlayerSlot++;
	}
	
	public Integer exitGame( int playerNumber ) {
		playerTable.remove(playerNumber);
		if(playerTable.isEmpty())
			m.stop();
		return nextPlayerSlot;

	}

	@Override
	public WebSocket doWebSocketConnect( HttpServletRequest arg0, String arg1) {
		return new JettyWebSocketConnection( new Handler() {

			@Override
			public void onConnectionOpened(Connection connection)
					throws Throwable {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onConnectionClosed(Connection connection)
					throws Throwable {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onExceptionCaught(Connection connection,
					Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMessageReceived(Connection connection, Object message)
					throws Throwable {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
