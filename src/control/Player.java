package control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fourx.civilizations.Civilization;
import com.fourx.civilizations.PerfectCivilization;
import com.fourx.research.TechnologyTree;
import com.fourx.research.Upgrades;
import com.fourx.resources.Resources;

import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.units.Unit;

public class Player {

	private final String name;

	private List<Unit> units;
	private List<Unit> selectedUnits;

	private List<Building> buildings;
	private List<Building> selectedBuildings;
	private Set<ResourceBuilding> resourceBuildings;

	public TechnologyTree techTree;
	public Upgrades upgrades;
	private Resources resources;
	private Civilization civ;

	// Bare constructor
	public Player() {
		this("", new PerfectCivilization());
	}

	public Player(String alias, Civilization civ) {
		this(alias, new Resources(0, 0, 0, 0), civ);
	}

	public Player(String alias, Resources resources, Civilization civ) {
		name = alias;
		// civilization
		this.civ = civ;

		// resources
		this.resources = resources;

		// units
		units = new ArrayList<Unit>();
		buildings = new ArrayList<Building>();
		selectedUnits = new ArrayList<Unit>();
		selectedBuildings = new ArrayList<Building>();
		resourceBuildings = new HashSet<ResourceBuilding>();

		// technology
		upgrades = new Upgrades();
		techTree = new TechnologyTree(this);
	}

	public String getAlias() {
		return name;
	}

	public void selectUnit(Unit unit) {
		selectedUnits.add(unit);
	}

	// public HashMap<String, String> possibleActions() {
	// HashMap<String, String> temp = new HashMap<String, String>();
	//
	// }

	public void addBuilding(Building building) {
		buildings.add(building);
	}

	public void selectBuilding(Building building) {
		selectedBuildings.add(building);
	}

	public Set<ResourceBuilding> getResourceBuildings() {
		return resourceBuildings;
	}

	public Resources getResources() {
		return resources;
	}

	public TechnologyTree getTechTree() {
		return techTree;
	}

	public Civilization getCivilization() {
		return civ;
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
}
