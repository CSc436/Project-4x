package control;

import java.util.HashMap;
import java.util.UUID;

import entities.Action;
import entities.Civilization;
import entities.PerfectCivilization;
import entities.PlayerUnits;
import entities.buildings.Building;
import entities.research.TechnologyTree;
import entities.research.Upgrades;
import entities.resources.Resources;
import entities.units.Unit;
import entities.units.agents.Goods;

public class Player {

	private final String name;
	private int id;

	private PlayerUnits objects; // the objects that the player owns, including
									// the currently selected units

	public TechnologyTree techTree;
	public Upgrades upgrades;
	public static Resources resources;
	public static Goods goods;
	private Civilization civ;
	private CommandQueue cq;

	private HashMap<UUID, Unit> selectedUnits;
	private HashMap<UUID, Building> selectedBuildings;

	// Bare constructor 
	public Player(String name, int id) {
		this(name, id, new PerfectCivilization());
	}

	public Player(String alias, int id, Civilization civ) {
		this(alias, id, civ, new Resources(0, 0, 0, 0, 0));
	}

	public Player(String alias, int id, Civilization civ, Resources resources) {
		name = alias;
		// civilization

		this.civ = civ;

		// resources
		this.resources = resources;

		// units
		objects = new PlayerUnits(id);
		selectedUnits = new HashMap<UUID, Unit>();
		selectedBuildings = new HashMap<UUID, Building>();
		

		// technology
		upgrades = new Upgrades();
		techTree = new TechnologyTree(this);
		cq = new CommandQueue();
		this.id = id;
	}

	public String getAlias() {
		return name;
	}

	public int getId() {
		return id;
	}

	public PlayerUnits getGameObjects() {
		return objects;
	}

	public static Resources getResources() {
		return resources;
	}
	
	public static int getGoodsNumber(Goods goods){
		return Goods.number;
	}
	public TechnologyTree getTechTree() {
		return techTree;
	}

	public Civilization getCivilization() {
		return civ;
	}

	public CommandQueue getCommandQueue() {

		return cq;
	}

	public void addActionTo(Unit u, Action a) {

		cq.push(a, u);
		// u.addAction(a);
	}

	public Building getBuilding(int buildingId) {
		return objects.getBuildings().get(buildingId);
	}

	public void selectUnit(Unit u) {

		selectedUnits.put(u.getId(), u);
	}

	public void selectBuilding(Building b) {

		selectedBuildings.put(b.getId(), b);

	}

	public void deselectBuilding(Building b) {

		selectedBuildings.remove(b);

	}

	public void addBuilding(Building b) {
		objects.addBuilding(b);

	}

	public void deselect() {
		selectedBuildings = new HashMap<UUID, Building>();
		selectedUnits = new HashMap<UUID, Unit>();
	}

	// !needs a test
	public boolean hasSelectedEligibleBuilding() {

		if (!selectedBuildings.isEmpty()) {
			Object[] temp = selectedBuildings.values().toArray();
		
			for (int i = 0; i < temp.length; i++) {
				Building b = (Building) temp[i];
				System.out.println(b.getBuildingType());
				if (b.getBuildingType() == BuildingType.BARRACKS)
					return true;

			}
			return false;
		} else {
			return false;
		}
	}
}
