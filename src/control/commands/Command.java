package control.commands;


import java.io.Serializable;

import control.GameModel;

//if a non startup/quit command, first argument in payload must be unique player id
public interface Command extends Serializable {

	public boolean validateCommand(GameModel model);
	
	public boolean performCommand(GameModel model);
}
