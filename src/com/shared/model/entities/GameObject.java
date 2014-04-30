package com.shared.model.entities;

import java.io.Serializable;
import java.util.HashMap;

import com.shared.model.behaviors.Attackable;
import com.shared.model.behaviors.Combatable;
import com.shared.model.behaviors.Movable;
import com.shared.model.behaviors.StandardHealth;
import com.shared.model.behaviors.StandardMover;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.stats.UnitStats;
import com.shared.utils.PhysicsVector;

public class GameObject implements Serializable, Combatable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -449525545957280452L;
	private final int id;
	private final int playerId;
	protected HashMap<String, String> allActions;
	protected GameObjectType type;
	protected UnitStats stats;
	protected BaseStatsEnum baseStats;
	protected Movable moveBehavior;
	protected Attackable healthBehavior;

	
	public GameObject() {
		this.id = 0;
		this.playerId = 0;
		this.baseStats = BaseStatsEnum.INFANTRY;
		stats = baseStats.getStats();
		updateStats(baseStats, stats);
		this.type = GameObjectType.ALL;
		PhysicsVector position = new PhysicsVector(0,0);
		moveBehavior = new StandardMover( position, stats.movementSpeed, stats.movementSpeed / 2.0 );
		healthBehavior = new StandardHealth( stats.health, stats.health_regen, stats.armor );
	}

	public GameObject(int id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, GameObjectType type, float xco, float yco) {
		this.id = id;
		this.playerId = playerId;
		this.baseStats = baseStats;
		stats = baseStats.getStats();
		updateStats(baseStats, new_stats);
		this.type = type;
		PhysicsVector position = new PhysicsVector(xco,yco);
		moveBehavior = new StandardMover( position, stats.movementSpeed, stats.movementSpeed / 2.0 );
		healthBehavior = new StandardHealth( stats.health, stats.health_regen, stats.armor );
	}

	public int getId() {
		return id;
	}

	
	public int getPlayerID() {
		return playerId;
	}

	/**
	 * called to reset the stats of the unit to (basestats + upgrades)
	 */

	public void updateStats(BaseStatsEnum s, UnitStats new_stats) {
		if (this.baseStats == s) {
			float percentage_health = stats.health / (float) stats.max_health;
			stats = new_stats.clone();
			stats.health = stats.max_health * percentage_health;
		}
	}
	
	public Movable getMoveBehavior() {
		return moveBehavior;
	}
	
	public Attackable getHealthBehavior() {
		return healthBehavior;
	}

	/**
	 * 
	 * @param n
	 *            - the amount of health to modify the unit by. can be a
	 *            negative amount to specify damage.
	 * @return - whether the unit is still alive.
	 */
	public boolean modifyHealthBy(float n) {

		stats.health = stats.health + n;
		if (stats.health > stats.max_health)
			stats.health = stats.max_health;

		if (stats.health <= 0)
			return false;
		else
			return true;

	}

	/**
	 * 
	 * @return - the current health of the Unit.
	 */
	public float getHealth() {
		return stats.health;
	}

	public UnitStats getStats() {
		return stats;
	}

	@Override
	public void takeDamage(int damage) {
		healthBehavior.takeDamage(damage);
	}

	@Override
	public PhysicsVector getPosition() {
		return moveBehavior.getPosition();
	}

	@Override
	public void setMoveTarget(double x, double y) {
		moveBehavior.setMoveTarget(x, y);
	}

	@Override
	public void simulateMovement(int timeStep) {
		moveBehavior.simulateMovement(timeStep);
	}

	@Override
	public PhysicsVector extrapolatePosition(int timeStep) {
		return moveBehavior.extrapolatePosition(timeStep);
	}

	@Override
	public void simulateDamage(int timeStep) {
		healthBehavior.simulateDamage(timeStep);
	}

	@Override
	public boolean isDead() {
		return healthBehavior.isDead();
	}

}
