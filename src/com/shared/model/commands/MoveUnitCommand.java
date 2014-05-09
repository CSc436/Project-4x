package com.shared.model.commands;

import com.shared.model.behaviors.Attacker;
import com.shared.model.control.GameModel;
import com.shared.model.entities.GameObject;
import com.shared.model.units.Unit;

public class MoveUnitCommand implements Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3924368375602014687L;
	private int entityID;
	private double x;
	private double y;
	
	public MoveUnitCommand() {}
	
	public MoveUnitCommand( int entityID, double x, double y ) {
		this.entityID = entityID;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {
		GameObject o = model.getGameObject(entityID);
		if( o != null ) {
			o.setMoveTarget(x, y);
			if( o instanceof Attacker ) ((Attacker) o).stopAttack();
		}
		return true;
	}

}
