package com.shared.model.commands;

import com.shared.model.behaviors.Attacker;
import com.shared.model.behaviors.Combatable;
import com.shared.model.control.GameModel;
import com.shared.model.entities.GameObject;

public class AttackCommand implements Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3042396025898165513L;
	private int attackerID;
	private int targetID;
	
	public AttackCommand(int attackerID, int targetID) {
		this.attackerID = attackerID;
		this.targetID = targetID;
	}
	
	public AttackCommand() {}

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {
		GameObject attacker = model.getGameObject(attackerID);
		GameObject target = model.getGameObject(targetID);
		if( attacker != null && attacker instanceof Attacker && target != null ) {
			((Attacker) attacker).setTarget(target);
			((Attacker) attacker).startAttack();
		}
		return true;
	}

}
