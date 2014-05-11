package com.client;

// FINDBUG - Reorganzied import statments
import java.util.Queue;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.model.commands.Command;
import com.shared.model.control.CommandPacket;
import com.shared.model.control.GameModel;
import com.shared.model.gameboard.Tile;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("simple_sim")
public interface SimpleSimulator extends RemoteService {

	CommandPacket sendCommands(int playerNumber, Queue<Command> commandQueue);

	String startSimulation();

	GameModel getGame(int playerNumber, int lastTurnReceived);

	String confirmReceipt(int playerNumber, int turnNumber);

	Integer joinGame();

	boolean login(String username);
	
	Integer exitGame( int playerNumber );
	
	Tile[][] getTiles();
}
