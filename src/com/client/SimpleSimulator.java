package com.client;

import java.util.Queue;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.MovingUnitModel;
import com.shared.Request;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("simple_sim")
public interface SimpleSimulator extends RemoteService {
	
	Request[] sendRequest(Request input) throws IllegalArgumentException;
	
	MovingUnitModel sendRequests( Queue<Request> requestQueue );
	
	String startSimulation();
	
	MovingUnitModel getSimulationState( int playerNumber, int lastTurnReceived );
	
	String confirmReceipt( int playerNumber, int turnNumber ); 
	
	Integer joinSimulation();
	
	Integer exitGame( int playerNumber );
	
}
