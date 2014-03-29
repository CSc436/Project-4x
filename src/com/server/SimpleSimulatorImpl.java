package com.server;

import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

import com.client.SimpleSimulator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.shared.MovingUnitModel;
import com.shared.Request;
import com.shared.SimpleGameModel;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SimpleSimulatorImpl extends RemoteServiceServlet implements
		SimpleSimulator {
	
	ModelController m = new ModelController();
	int currentTurn;
	boolean debug = false;
	
	HashMap<Integer, Boolean> playerTable = new HashMap<Integer, Boolean>();
	
	int nextPlayerSlot = 0;

	public Request[] sendRequest(Request input) throws IllegalArgumentException {
		
		// Verify that the input is valid.

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		
		

		// Escape data from the client to avoid cross-site script vulnerabilities.
		userAgent = escapeHtml(userAgent);
		
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

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
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
}
