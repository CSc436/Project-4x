package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;

import control.Player;
import entities.GameObject;
import entities.units.Unit;

public abstract class Building extends GameObject{

	// buildings will be > 1 tile

	// initial building types

	// unit producing
	// resource producing
	// research options
	// defense units
	
	
	//garrison?
	
	private int maxHp;
	protected static int maxLevel;
	private int health;
	private Player owner; // owner of the structure
	private int height; // height of the structure
	private int width; // width of the structure
	protected static int level;
	private int x = -1;
	private int y = -1; // dimensions on the game board

	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	public Building(Player p, int h, int w, int hp, int lv) {

		maxHp = health = hp;
		owner = p;
		height = h;
		width = w;
		level = lv;

	}

	// GET / SET
	public int getHealth() {
		return health;
	}

	public void setHealth(int hp) {
		if (hp > maxHp)
			health = maxHp;
		else if (hp < 0)
			health = 0;
		else
			health = hp;
	}

	/*
	 * This method modifies the health Use: hp - Negative values will afflict
	 * damage to the health - Positive values will "heal" the building
	 * (restoring health)
	 * 
	 * Can't heal a building past its maximum health. Return -- true if unit is
	 * still alive after modification, false if dead.
	 */
	public boolean modifyHealth(int hp) {
		health += hp;
		if (health > maxHp)
			health = maxHp;

		return health > 0;
	}

	public Player getOwner() {
		return owner;
	}

	public void setLocation(int row, int col) {
		x = row;
		y = col;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public static int getLevel() {
		return level;
	}
	
	protected void upgrade(){
		if(level==maxLevel)
			System.out.println("Your building has reached the highest level!");
		else
			level++;
	}
	/*
	 * Holding the Queue for the units that the building is responsible for
	 * producing. It can add units to its queue or remove finished units from
	 * its queue
	 */
	public boolean queueUnit(Unit u) {
		return buildingQ.offer(u);
	}

	public Unit dequeueUnit() {
		return buildingQ.poll();

	}

}
