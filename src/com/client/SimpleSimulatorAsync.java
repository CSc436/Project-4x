package com.client;

import java.util.Queue;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.shared.MovingUnitModel;
import com.shared.Request;
import com.shared.SimpleGameModel;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SimpleSimulatorAsync {
	void sendRequest(Request input, AsyncCallback<Request[]> callback)
			throws IllegalArgumentException;
	
	void sendRequests( Queue<Request> requestQueue, AsyncCallback<SimpleGameModel> callback);
	
	void startSimulation(AsyncCallback<String> callback);
	
	void getSimulationState(int playerNumber, int lastTurnReceived, AsyncCallback<SimpleGameModel> callback);
	
	void confirmReceipt(int playerNumber, int turnNumber, AsyncCallback<String> callback);
	
	void joinSimulation(AsyncCallback<Integer> callback);
	
	void exitGame(int playerNumber, AsyncCallback<Integer> callback);

}

