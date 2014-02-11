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

	}

	public void setName(String newName) {

		name = newName;
	}

	public void addUnit(Unit u) {
		units.add(u);
	}
	

	
	
}
