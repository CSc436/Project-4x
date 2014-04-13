package control.commands;

import control.GameModel;
import entities.GameObject;
import entities.units.Unit;

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

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performCommand(GameModel model) {
		Unit attacker = (Unit) model.getGameObject(attackerID);
		GameObject target = model.getGameObject(targetID);
		attacker.getAttackBehavior().setTarget(target);
		// TODO Auto-generated method stub
		return false;
	}

}
