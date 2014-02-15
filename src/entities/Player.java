package entities;

import java.util.ArrayList;

import entities.buildings.Building;
import entities.units.Unit;

public class Player {

	// scoring
	// ints for resource counts

	private String name;
	private ArrayList<Unit> units;
	private ArrayList<Building> buildings;

	public Player(String alias) {
		name = alias;
		units = new ArrayList<Unit>();
		buildings = new ArrayList<Building>();

	}

	public void setName(String newName) {

		name = newName;
	}

	public void addUnit(Unit u) {
		units.add(u);
	}

	public void addBuilding(Building b) {
		buildings.add(b);
	}

	public String getName() {
		return name;
	}

	public void removeUnit(Unit u) {
		units.remove(u);
	}

	public void removeBuilding(Building b) {
		buildings.remove(b);
	}

	public boolean hasUnitAt(int x, int y) {

		for (int c = 0; c < units.size(); c++) {
			if (units.get(c).getX() == x && units.get(c).getY() == y)
				return true;
		}
		return false;

	}

	public boolean hasBuildingAt(int x, int y) {

		for (int c = 0; c < buildings.size(); c++) {
			if (buildings.get(c).getX() == x && buildings.get(c).getY() == y)
				return true;
		}
		return false;

	}
}
