package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;

import control.Player;
import entities.UnitType;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public abstract class Building extends Unit {

	protected int width;
	protected int height;

	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	public Building(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int idno) {
		this(p, baseStats, type, xco, yco, 1, 1, idno);
	}

	public Building(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int height, int width, int idno) {
		super(p, baseStats, type, xco, yco, idno);
		this.height = height;
		this.width = width;
		p.getUnits().addBuilding(this);
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