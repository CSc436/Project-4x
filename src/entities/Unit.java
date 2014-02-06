package entities;
/*
 * Programmer:  Benjamin Deininger
 * 
 * Purpose:  This abstract class defines the concept of a unit.  Every unit will atleast have the following 
 * information known about itself.  
 */

public abstract class Unit {

	private int health;
	private Player owner;

	public Unit(Player p) {

		health = 100;
		owner = p;

	}

	// GET / SET
	public int getHealth() {
		return health;
	}

	public Player getOwner() {
		return owner;
	}

}
