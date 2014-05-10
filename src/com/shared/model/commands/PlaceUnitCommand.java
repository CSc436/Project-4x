package com.shared.model.commands;

import com.shared.model.control.GameModel;
import com.shared.model.units.UnitType;
import com.shared.utils.Coordinate;

public class PlaceUnitCommand implements Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4509177028921536960L;
	private UnitType type;
	private int playerNumber;
	private Coordinate position;
	
	public PlaceUnitCommand( UnitType ut, int pn, Coordinate c ) {
		type = ut;
		this.playerNumber = pn;
		position = c;
	}
	
	public PlaceUnitCommand() {}
	
	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {
		model.createUnit(type, playerNumber, position);
		return true;
	}

}
