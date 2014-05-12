package com.client;

import java.util.Queue;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.model.commands.Command;
import com.shared.model.control.CommandPacket;
import com.shared.model.control.GameModel;
import com.shared.model.gameboard.Tile;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SimpleSimulatorAsync {

	void sendCommands(int playerNumber, Queue<Command> commandQueue,
			AsyncCallback<CommandPacket> callback);

	void startSimulation(AsyncCallback<String> callback);

	void getGame(int playerNumber, int lastTurnReceived,
			AsyncCallback<GameModel> callback);

	void confirmReceipt(int playerNumber, int turnNumber,
			AsyncCallback<String> callback);

	void joinGame(AsyncCallback<Integer> callback);

	void exitGame(int playerNumber, AsyncCallback<Integer> callback);
	
	void getUntrimmedTiles(AsyncCallback<Tile[][]> callback);

	void login(String username, AsyncCallback<Boolean> callback);

}
