package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;

import com.fourx.buffs.UnitType;

import control.Player;
import entities.BaseStatsEnum;
import entities.units.Unit;

public abstract class Building extends Unit {

	// buildings will be > 1 tile

	// initial building types

	// unit producing
	// resource producing
	// research options
	// defense units

	// garrison?

	protected int width;
	protected int height;

	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	public Building(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco) {
		this(p, baseStats, type, xco, yco, 1, 1);
	}

	public Building(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int height, int width) {
		super(p, baseStats, type, xco, yco);
		this.height = height;
		this.width = width;
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
}