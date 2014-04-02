package control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import entities.Action;
import entities.Civilization;
import entities.PerfectCivilization;
import entities.PlayerUnits;
import entities.buildings.Building;
import entities.research.TechnologyTree;
import entities.research.Upgrades;
import entities.resources.Resources;
import entities.units.Agent;
import entities.units.Unit;
import entities.units.agents.General;
import entities.units.agents.Goods;
import entities.units.agents.Merchant;

public class Player {

	private final String name;
	private int id;
	public Map<Agent,Integer> prestige;
	public Map<Goods,Integer> goodsNumber;
	private PlayerUnits objects; // the objects that the player owns, including
									// the currently selected units
	private TechnologyTree techTree;
	private Upgrades upgrades;
	public Resources resources;
	public Goods goods;
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

		// technology
		upgrades = new Upgrades();
		techTree = new TechnologyTree(this);
		cq = new CommandQueue();
		this.id = id;
		
		// init blabhlahb
		selectedBuildings = new HashMap<UUID, Building>();
		selectedUnits = new HashMap<UUID, Unit>();
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

	public Resources getResources() {
		return resources;
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

	public Upgrades getUpgrades() {
		return upgrades;
	}

	public void addActionTo(Unit u, Action a) {
		cq.push(a, u);
	}

	public Building getBuilding(UUID buildingId) {
		return objects.getBuildings().get(buildingId);
	}

	public Unit getUnit(UUID id2) {
		return objects.getUnits().get(id2);
	}

	public void addBuilding(Building b) {
		objects.addBuilding(b);
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

	public void deselect() {
		selectedBuildings = new HashMap<UUID, Building>();
		selectedUnits = new HashMap<UUID, Unit>();
	}

	
	// TODO implement the right way? Graham 
	public int getGoodsNumber(Goods g)
	{
		return 1;
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
