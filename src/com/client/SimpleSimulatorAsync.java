package com.client;

import java.util.Queue;

import com.google.gwt.user.client.rpc.AsyncCallback;

import control.GameModel;
import control.commands.Command;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SimpleSimulatorAsync {
	void sendCommand(Command input, AsyncCallback<Void> callback)
			throws IllegalArgumentException;
	
	void sendCommands( Queue<Command> commandQueue, AsyncCallback<GameModel> callback);
	
	void startSimulation(AsyncCallback<String> callback);
	
	void getSimulationState(int playerNumber, int lastTurnReceived, AsyncCallback<GameModel> callback);
	
	void confirmReceipt(int playerNumber, int turnNumber, AsyncCallback<String> callback);
	
	void joinSimulation(AsyncCallback<Integer> callback);
	
	void exitGame(int playerNumber, AsyncCallback<Integer> callback);

}

