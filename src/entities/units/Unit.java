package entities.units;

import java.util.PriorityQueue;

import control.Player;
import entities.Action;
import entities.UnitType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

/*
 * 
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

public abstract class Unit {

	private Player owner;

	protected UnitType type;
	private UnitStats stats;
	protected BaseStatsEnum baseStats;

	private int x;
	private int y;
	private PriorityQueue<Action> actionQueue;

	private int id;

	public Unit(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int idno) {

		this.baseStats = baseStats;
		this.type = type;

		owner = p;
		id = idno;
		updateStats();

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
	 * @param b
	 *            - x coordinate of the unit.
	 * @param c
	 *            - y coordinate of the unit.
	 */
	public void setLocation(int b, int c) {
		x = b;
		y = c;
	}

	/**
	 * 
	 * @param n
	 *            - set the health of the Unit to the specified amount.
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
		getOwner().getCommandQueue().push(a, this);
	}

	/**
	 * Logic to handle actions that the Unit may do. TODO: add all necessary
	 * actions
	 */
	public void performActions() {
		while (!actionQueue.isEmpty()) {
			Action a = actionQueue.poll();
			switch (a) {
			case PLAYER_ATTACK_MOVE:
				System.out.println("player attack move");
				break;
			case PLAYER_ATTACK:
				System.out.println("player attack");
				break;
			case DEATH:
				// I DIED
				System.out.println("death at :" + x + " " + y);
				getOwner().getUnits().removeUnit(this);
				while (!actionQueue.isEmpty())
					actionQueue.poll();
				break;
			default:
				break;
			}
			// TODO: do stuff
		}
	}

	public PriorityQueue<Action> getActionQueue() {
		return actionQueue;
	}
}
