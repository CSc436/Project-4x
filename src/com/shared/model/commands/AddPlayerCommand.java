package com.shared.model.commands;

import com.shared.model.control.GameModel;

public class AddPlayerCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1214325216664660506L;
	private String playerName;
	private int playerID;
	
	public AddPlayerCommand( String playerName ) {
		this.playerName = playerName;
		this.playerID = -1;
	}
	
	public AddPlayerCommand (String playerName, int playerID) {
		this.playerName = playerName;
		this.playerID = playerID;
	}
	
	public AddPlayerCommand() {}
	
	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {
		if (playerID == -1 ) {
			model.addPlayer(playerName);
		} else {
			model.addPlayer(playerID, playerName);
		}
		return true;
	}
	
}
