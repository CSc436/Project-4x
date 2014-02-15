package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Building;
import entities.Unit;

public class Player {

	// scoring
	// ints for resource counts

	private String name;
	private List<Unit> selectedUnits;
	private List<Building> selectedBuildings;
	
	public Player(String alias) {
		name = alias;
		selectedUnits = new ArrayList<Unit>();
		selectedBuildings = new ArrayList<Building>();

	}

	public void setName(String newName) {

		name = newName;
	}
	
	public void selectUnit(Unit unit) {
		selectedUnits.add(unit);
	}
	
	public HashMap<String, String> possibleActions() {
		HashMap<String, String> temp = new HashMap<String, String>();
		
	}
	
	public void selectBuilding(Building building) {
		selectedBuildings.add(building);
	}
	
	

}
