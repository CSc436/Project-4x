package com.shared.model.commands;

import com.shared.model.control.GameModel;

/**
 * 
 * @author NRTop
 * SendMessageCommand
 * Used to send messages via the chat interface.
 */
public class SendMessageCommand implements Command
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -396455197222500915L;
	private String message; 
	
	public SendMessageCommand() {}

	/**
	 * constructor. used to create a new message to be processed by other games.
	 * @param msg
	 */
	public SendMessageCommand(String msg)
	{
		this.message = msg; 
	}
	// Automatically validate the command
	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	/**hhhhhh
	 * 
	 * @param GameModel model - the model of the game this chat message command exists in.
	 */
	public boolean performCommand(GameModel model) {
		// TODO Auto-generated method stub
		// model.getInterface.updateMessages?
		// or add some sort of flag so that interface knows to look at the gameModel.
		model.updateChatLog(message);
		return true;
	}

}
