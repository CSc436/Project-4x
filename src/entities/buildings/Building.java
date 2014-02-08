package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;

import entities.Player;
import entities.units.Unit;

public abstract class Building {

	// buildings will be > 1 tile

	// initial building types

	// unit producing
	// resource producing
	// research options
	// defense units

	private int health;
	private Player owner; // owner of the structure
	private int height; // height of the structure
	private int width; // width of the structure
	private int x = -1;
	private int y = -1; // dimensions on the game board

	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	public Building(Player p, int h, int w) {

		health = 100;
		owner = p;
		height = h;
		width = w;

	}

	// GET / SET
	public int getHealth() {
		return health;
	}

	public Player getOwner() {
		return owner;
	}

	public void setLocation(int row, int col) {

		x = row;
		y = col;
	}

	public boolean queueUnit(Unit u) {

		return buildingQ.offer(u);

	}

	public void dequeueUnit() {

		
		
	}
}
