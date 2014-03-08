package com.client;

import java.util.Queue;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.MovingUnit;
import com.shared.Request;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SimpleSimulatorAsync {
	void sendRequest(Request input, AsyncCallback<Request[]> callback)
			throws IllegalArgumentException;
	
	void sendRequests( Queue<Request> requestQueue, AsyncCallback<MovingUnit> callback);
	
	void startSimulation(AsyncCallback<String> callback);
	
	void getSimulationState(int turnNumber, AsyncCallback<MovingUnit> callback);
	
	void confirmReceipt(int turnNumber, AsyncCallback<String> callback);
}
