package com.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.MovingNumber;
import com.shared.Request;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SimpleSimulatorAsync {
	void sendRequest(Request input, AsyncCallback<Request[]> callback)
			throws IllegalArgumentException;
	
	void startSimulation(AsyncCallback<String> callback);
	
	void getSimulationState(int turn, AsyncCallback<MovingNumber> callback);
}
