package entities.units;

import java.util.PriorityQueue;

import com.fourx.buffs.UnitType;

import control.Player;
import entities.Action;
import entities.BaseStatsEnum;
import entities.GameObject;
import entities.UnitStats;

/*
 * Programmer:  Benjamin Deininger
 * 
 * Purpose:  This abstract class defines the concept of a unit.  Every unit will atleast have the following 
 * information known about itself.  
 */

// infantry
// calvary
// ranged
// water?
// transport?
// trade cart
// settler / worker
// healing unit

public abstract class Unit extends GameObject {

	private Player owner;

	private UnitType type = UnitType.INFANTRY;
	private BaseStatsEnum baseStats;

	private UnitStats stats;

	private int x;
	private int y;
	private PriorityQueue<Action> actionQueue;

	public Unit(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco) {
		this.baseStats = baseStats;
		this.type = type;
		updateStats();

		owner = p;
		p.getUnits().addUnit(this);
		actionQueue = new PriorityQueue<Action>();
		x = xco;
		y = yco;
	}

	/**
	 * called to reset the stats of the unit to (basestats + upgrades)
	 */
	public void updateStats() {
		stats = baseStats.augment(stats, type.getStats(owner));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param b - x coordinate of the unit.
	 * @param c - y coordinate of the unit.
	 */
	public void setLocation(int b, int c) {
		x = b;
		y = c;
	}

	/**
	 * 
	 * @param n - set the health of the Unit to the specified amount.
	 * @return - whether the unit is still alive.
	 */
	public boolean setHealth(int n) {
		stats.health = n;
		if (stats.health > stats.max_health)
			stats.health = stats.max_health;

		if (stats.health <= 0)
			return false;
		else
			return true;

	}

	/**
	 * 
	 * @param n
	 *            - the amount of health to modify the unit by. can be a
	 *            negative amount to specify damage.
	 * @return - whether the unit is still alive.
	 */
	public boolean modifyHealth(int n) {

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

	public Player getOwner() {
		return owner;
	}

	/**
	 * 
	 * @param a
	 *            - add an action to the PriorityQueue to be performed during
	 *            the turn.
	 */
	public void addAction(Action a) {
		actionQueue.add(a);
	}

	/**
	 * Logic to handle actions that the Unit may do.
	 */
	public void performActions() {
		while (!actionQueue.isEmpty()) {
			Action a = actionQueue.poll();
			switch (a) {
			case PLAYER_ATTACK_MOVE:
				break;
			case DEATH:
				// I DIED
				System.out.println(x);
				getOwner().getUnits().removeUnit(this);
			default:
				break;
			}
			// TODO: do stuff
		}
	}
}
