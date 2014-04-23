package entities.units.behavior;

import control.GameModel;
import entities.GameObject;
import entities.units.Unit;

public abstract class AttackBehavior {
	protected GameObject target;
	protected int coolDown; // Number of milliseconds for attack cooldown
	protected int coolDownTimer = 0; // Number of milliseconds since previous attack

	
	//Use graham's stuff
	public abstract void attack(Unit currentUnit, int timeStep);
	
	/**
	 * depended on LOS, range, and 'desire' to attack
	 * if can attack, calls setAttack Target to 
	 * 
	 * @param model
	 * @return
	 */
	public abstract boolean canAttack(GameModel model, Unit currentUnit);
	
	//must be called before attack
	public abstract void setAttackTarget(GameObject newTarget);
	
	/**
	 * called when target out of range/dead/attack canceled
	 */
	public abstract void resetAttackTarget();

	public abstract boolean enemyInSight(GameModel model, Unit currentUnit);
	
	public abstract boolean isAttacking();
}