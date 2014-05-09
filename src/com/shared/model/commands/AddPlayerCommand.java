package com.shared.model.commands;

import com.shared.model.control.GameModel;

public class AddPlayerCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1214325216664660506L;
	private String playerName;
	
	public AddPlayerCommand( String playerName ) {
		this.playerName = playerName;
	}
	
	public AddPlayerCommand() {}
	
	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {
		model.addPlayer(playerName);
		return true;
	}
	
}
