package com.shared.commands;

import java.util.List;

import com.fourx.util.Point;
import com.server.FourXGameModel;

public class MoveCommand extends GameCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3858989322919866446L;
	private List<Integer> unitIDs; // A list of unit ID numbers
	private Point destination;

	public MoveCommand(int turnSentOn, int playerID, List<Integer> unitIDs, Point destination ) {
		super(turnSentOn,playerID);
		this.unitIDs = unitIDs;
		this.destination = destination;
	}

	@Override
	public boolean executeOn(FourXGameModel model) {
		for( int i : unitIDs ) {
			// Set each unit's target location to the destination
			//model.getUnit(i).setTarget(destination);
		}
		return false;
	}
	
	

}
