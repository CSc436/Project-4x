package com.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.client.SimpleSimulator;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.shared.Model;
import com.shared.MovingUnit;
import com.shared.Request;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SimpleSimulatorImpl extends RemoteServiceServlet implements
		SimpleSimulator {
	
	Model m = new Model();
	Thread modelThread = new Thread(m);
	int currentTurn;
	
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
	
	public MovingUnit sendRequests( Queue<Request> requestQueue ) {
		/*
		while( !requestQueue.isEmpty() ) {
			Request r = requestQueue.remove();
			r.executeOn(m);
		}
		*/
		m.simulateFrame();
		return m.getUnit();
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
		
		if(!modelThread.isAlive())
			modelThread.start();
		
		return null;
	}
	
	// Confirm that the most recent simulation state was received, prevents
	@Override
	public String confirmReceipt( int turnNumber ) {
		m.continueSimulation();
		return null;
	}

	@Override
	public MovingUnit getSimulationState( int playerNumber ) {
		//while(m.turnNumber != turnNumber);
		playerTable.put(playerNumber, true);
		return m.getUnit();
	}
	
	public Integer joinSimulation() {
		if(!modelThread.isAlive())
			modelThread.start();
		playerTable.put(nextPlayerSlot++, false);
		return nextPlayerSlot;
	}
	
	public Integer exitGame( int playerNumber ) {
		playerTable.remove(playerNumber);
		if(playerTable.isEmpty())
			m.stop();
		return nextPlayerSlot;
	}
}
