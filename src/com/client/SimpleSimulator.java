package com.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.MovingUnit;
import com.shared.Request;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("simple_sim")
public interface SimpleSimulator extends RemoteService {
	
	Request[] sendRequest(Request input) throws IllegalArgumentException;
	
	String startSimulation();
	
	MovingUnit getSimulationState();
	
}
