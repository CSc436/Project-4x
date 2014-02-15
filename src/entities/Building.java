package entities;

import control.Player;

public abstract class Building extends GameObject{

	// buildings will be > 1 tile
	// int height
	// int width
	// int,int coordinates // or point

	// initial building types

	// unit producing
	// resource producing
	// research options
	// defense units

	private int health;
	private Player owner;

	public Building(Player p) {

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
