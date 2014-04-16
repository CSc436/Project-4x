package entities.units;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.UUID;

import com.shared.PhysicsVector;

import control.UnitType;
import entities.Action;
import entities.stats.BaseStatsEnum;
import entities.AttackBehavior;
import entities.Attacker;
import entities.GameObject;
import entities.GameObjectType;
import entities.MoveBehavior;
import entities.stats.UnitStats;

/*
 * 
 * 
 * Purpose:  This abstract class defines the concept of a unit.  Every unit will atleast have the following 
 * information known about itself.  
 */

// TODO add A* path finding, use diagonals to make nice looking paths
// returns a queue/list of tiles that it needs to go to, at each turn pop one off and move player there. 

public class Unit extends GameObject implements Attacker, Producible {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5568927886403172710L;
	private UnitType unitType;
	private int creationTime;
	private Attacker attackBehavior;
	
	public Unit() {
		super();
		this.unitType = UnitType.ARCHER;
		this.creationTime = baseStats.getCreationTime();
	}

	public Unit(int id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, UnitType unitType,
			float xco, float yco) {
		super(id, playerId, baseStats, new_stats, GameObjectType.UNIT, xco, yco);
		this.unitType = unitType;
		this.creationTime = baseStats.getCreationTime();
		attackBehavior = new AttackBehavior( stats.damage, stats.range, stats.actionSpeed, moveBehavior );
	}
	
	public Attacker getAttackBehavior() {
		return attackBehavior;
	}

	/**
	 * getActionQueue()
	 * returns the list of actions this unit is in process of doing. 
	 * @return
	 */
	public PriorityQueue<Action> getActionQueue() {
		return actionQueue;
	}

	/**
	 * getCreationTime():
	 * returns the creation time for this unit.
	 * @return
	 */
	public int getCreationTime() {
		return this.baseStats.getCreationTime();
	}
	
	public UnitType getUnitType() {
		return unitType;
	}
	
	/**
	 * decrementCreationTime()
	 * decrements remaining time for unit production
	 * @param int timestep - how much to decrement by
	 */
	public void decrementCreationTime(int timestep)
	{
		this.creationTime -= timestep;
	}
	
	public void advanceTimeStep( int timeStep ) {
		super.advanceTimeStep(timeStep);
		attackBehavior.advanceTimeStep(timeStep);
	}

	@Override
	public PhysicsVector getPosition() {
		return super.getPosition();
	}

	@Override
	public void setMoveTarget(double x, double y) {
		super.setMoveTarget(x, y);
	}

	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
	}

	@Override
	public void setTarget(GameObject target) {
		attackBehavior.setTarget(target);
	}

	@Override
	public void startAttack() {
		attackBehavior.startAttack();
	}

	@Override
	public void stopAttack() {
		attackBehavior.stopAttack();
	}

	@Override
	public int getProductionTime() {
		return 0;
	}
}
