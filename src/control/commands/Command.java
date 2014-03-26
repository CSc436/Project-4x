package control.commands;

import java.util.ArrayList;

import control.GameModel;

//if a non startup/quit command, first argument in payload must be unique player id
public interface Command {

	public boolean validateCommand(GameModel model);
	
	public boolean performCommand(GameModel model);
}
