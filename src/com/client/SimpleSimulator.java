package com.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.MovingNumber;
import com.shared.Request;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface SimpleSimulator extends RemoteService {
	Request[] sendRequest(Request input) throws IllegalArgumentException;
	String startSimulation();
	MovingNumber getSimulationState(int turn);
}
