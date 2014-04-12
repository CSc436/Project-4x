package entities.units.behavior;

import control.GameModel;
import entities.GameObject;

public abstract class AttackBehavior {
	protected GameObject target;
	
	//Use graham's stuff
	public abstract boolean attack(GameModel model);
	
	/**
	 * depended on LOS, range, and 'desire' to attack
	 * if can attack, calls setAttack Target to 
	 * 
	 * @param model
	 * @return
	 */
	public abstract boolean canAttack(GameModel model);
	
	//must be called before attack
	public abstract void setAttackTarget(GameObject newTarget);
	
	/**
	 * called when target out of range/dead/attack canceled
	 */
	public abstract void resetAttackTarget();

	public boolean enemyInSight(GameModel model) {
		//or if enemy already selected
		// TODO Auto-generated method stub
		return false;
	}
}