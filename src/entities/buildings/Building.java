package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;

import control.Player;
import entities.UnitType;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public abstract class Building extends Unit {
	public int id;

	public Building(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int idno) {
		this(p, baseStats, type, xco, yco, 1, 1, idno);
	}

	// buildings will be > 1 tile

	// initial building types

	// unit producing
	// resource producing
	// research options
	// defense units

	// garrison?

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

	public Building(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int height, int width, int idno) {
		super(p, baseStats, type, xco, yco, idno);
		this.height = height;
		this.width = width;
		id = idno;

	}

	public void setHeight(int x) {
		height = x;
	}

	public void setWidth(int x) {
		width = x;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getId() {
		return id;
	}

	public static int getLevel() {
		return level;
	}

	protected void upgrade() {
		if (level == maxLevel)
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
