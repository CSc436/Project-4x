package com.client;

import java.util.Queue;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.model.commands.Command;
import com.shared.model.control.GameModel;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("simple_sim")
public interface SimpleSimulator extends RemoteService {
	
	void sendCommand(Command input) throws IllegalArgumentException;
	
	GameModel sendCommands( Queue<Command> commandQueue );
	
	String startSimulation();
	
	GameModel getSimulationState( int playerNumber, int lastTurnReceived );
	
	String confirmReceipt( int playerNumber, int turnNumber ); 
	
	Integer joinSimulation();
	
	Integer exitGame( int playerNumber );
	
}
