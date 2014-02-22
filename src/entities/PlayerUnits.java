package entities;

import java.util.ArrayList;
import java.util.List;

import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.units.Agent;
import entities.units.Unit;

public class PlayerUnits {
	private List<Unit> units;
	private List<Unit> selectedUnits;

	private List<Building> buildings;
	private List<Building> selectedBuildings;
	private List<ResourceBuilding> resourceBuildings;

	private List<Agent> agents;

	public PlayerUnits() {
		units = new ArrayList<Unit>();
		buildings = new ArrayList<Building>();
		selectedUnits = new ArrayList<Unit>();
		selectedBuildings = new ArrayList<Building>();
		resourceBuildings = new ArrayList<ResourceBuilding>();
		agents = new ArrayList<Agent>();
	}

	public void selectUnit(Unit unit) {
		selectedUnits.add(unit);
	}

	public void addBuilding(Building building) {
		buildings.add(building);
	}

	public void selectBuilding(Building building) {
		selectedBuildings.add(building);
	}

	public List<ResourceBuilding> getResourceBuildings() {
		return resourceBuildings;
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

	public void addUnit(Unit u) {
		units.add(u);

	}

	public List<Unit> getSelectedUnitList() {
		return selectedUnits;
	}

	public List<Unit> getUnitList() {
		return units;
	}

	public void addAgent(Agent a) {

		agents.add(a);
	}

	public void removeAgent(Agent a) {
		agents.remove(a);
	}

	public List<Agent> getAgentList() {

		return agents;
	}

}
