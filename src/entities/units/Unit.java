package entities.units;

import entities.Player;

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

public abstract class Unit {

	private int health;
	private Player owner;
	private int baseDmg;
	private int augment;
	private int maxHp;
	private int movementRange;

	public Unit(Player p, int hp, int bDmg, int mR) {

		maxHp = health = hp;
		owner = p;
		baseDmg = bDmg;
		movementRange = mR;

	}

	public void setAugment(int n) {
		augment = n;

	}

	public void setMovementRange(int newMR) {
		movementRange = newMR;
	}

	public int getMovementRange() {
		return movementRange;
	}

	public int getDamage() {
		return baseDmg + augment;
	}

	// set health
	// return true or false if the unit is dead
	public boolean setHealth(int n) {

		health = n;
		if (health > maxHp)
			health = maxHp;

		if (health <= 0)
			return false;
		else
			return true;

	}

	// negative n will be damage
	// positive will be healing

	public boolean modifyHealth(int n) {

		health = health + n;
		if (health > maxHp)
			health = maxHp;

		if (health <= 0)
			return false;
		else
			return true;

	}

	// GET / SET
	public int getHealth() {
		return health;
	}

	public Player getOwner() {
		return owner;
	}

}
