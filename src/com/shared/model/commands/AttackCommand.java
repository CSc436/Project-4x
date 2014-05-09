package com.shared.model.commands;

import com.shared.model.behaviors.Attacker;
import com.shared.model.behaviors.Combatable;
import com.shared.model.control.GameModel;

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
		Attacker attacker = (Attacker) model.getGameObject(attackerID);
		Combatable target = (Combatable) model.getGameObject(targetID);
		attacker.setTarget(target);
		attacker.startAttack();
		return true;
	}

}
