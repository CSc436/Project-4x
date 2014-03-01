package com.shared;

import java.util.concurrent.ConcurrentLinkedDeque;

import com.client.SimpleSimulator;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SimpleSimulatorImpl extends RemoteServiceServlet implements
		SimpleSimulator {
	
	Model m = new Model();
	Thread modelThread = new Thread(m);
	int currentTurn;
	ConcurrentLinkedDeque<Request> requestQueue = new ConcurrentLinkedDeque<Request>();

	public Request[] sendRequest(Request input) throws IllegalArgumentException {
		// Verify that the input is valid.

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		
		

		// Escape data from the client to avoid cross-site script vulnerabilities.
		userAgent = escapeHtml(userAgent);
		
		input.executeOn(m);

		return new Request[] {input};
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
		modelThread.start();
		return null;
	}

	@Override
	public MovingUnit getSimulationState() {
		return m.getUnit();
	}
}
