package control;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import entities.Action;
import entities.Civilization;
import entities.PerfectCivilization;
import entities.PlayerUnits;
import entities.buildings.Building;
import entities.buildings.BuildingType;
import entities.research.TechnologyTree;
import entities.research.Upgrades;
import entities.resources.Resources;
import entities.units.Agent;
import entities.units.Unit;
import entities.units.agents.Goods;

/**
 * Player
 * 
 * @author NRTop
 * @description the player class keeps tracks of all statistics vital to an
 *              individual player. For example it keeps track of their
 *              resources, current techTree, Units, Buildings, etc. A player
 *              class should exist for each human/AI player playing a game of
 *              Legacy of Kings/Porkins.
 * 
 */
public class Player {

	private final String name; // name of player, use for display purposes and
								// chat
	private int id; // unique integer id of this player, allows lower level
					// objects such as units to access player info without
					// containing a player object ref.
	public Map<Agent, Integer> prestige;
	public Map<Goods, Integer> goodsNumber;
	private PlayerUnits objects; // the objects that the player owns, including
									// the currently selected units
	private TechnologyTree techTree;
	private Upgrades upgrades;
	public Resources resources; // current resources this player owns
	public Goods goods;
	private Civilization civ;
	private CommandQueue cq;

	private HashMap<UUID, Unit> selectedUnits; // units currently selected by
												// the player (those that can
												// receive commands?)
	private HashMap<UUID, Building> selectedBuildings; // buildings currently
														// selected by player
														// (possibly only want
														// to allow player to
														// select one at a
														// time?)

	/**
	 * Player():
	 * 
	 * @description This is a bare constructor, initializes a player with a
	 *              perfect civilization.
	 * 
	 *              Parameters:
	 * @param name
	 *            - name of the player
	 * @param id
	 *            - unique integer id of the player
	 */
	public Player(String name, int id) {
		this(name, id, new PerfectCivilization());
	}

	/**
	 * Player():
	 * 
	 * @description This constructor allows for the civilization of the player
	 *              to be specified. Initializes the resources of the player to
	 *              be 0 for all resources.
	 * 
	 * @param alias
	 *            - name of the player
	 * @param id
	 *            - unique integer id of the player
	 * @param civ
	 *            - civilization the player has
	 */
	public Player(String alias, int id, Civilization civ) {
		this(alias, id, civ, new Resources(0, 0, 0, 0, 0));
	}

	/**
	 * Player():
	 * 
	 * @description This constructor allows for both the civilization and
	 *              resources of the player to be specified
	 * 
	 * @param alias
	 *            - name of the player
	 * @param id
	 *            - unique integer id of the player
	 * @param civ
	 *            - civilization for the player
	 * @param resources
	 *            - resources for the player
	 */
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

	/**
	 * getAlias()
	 * 
	 * @return the name of the player
	 */
	public String getAlias() {
		return name;
	}

	/**
	 * getId()
	 * 
	 * @return the unique integer id of the player
	 */
	public int getId() {
		return id;
	}

	/**
	 * getGameObjects()
	 * 
	 * @return all gameObjects (units, buildings, agents, etc.) owned by the
	 *         player
	 */
	public PlayerUnits getGameObjects() {
		return objects;
	}

	/**
	 * getResources()
	 * 
	 * @return returns the current resources of the player, used to conduct
	 *         transactions
	 */
	public Resources getResources() {
		return resources;
	}

	/**
	 * getTechTree()
	 * 
	 * @return returns the techtree of the player
	 */
	public TechnologyTree getTechTree() {
		return techTree;
	}

	/**
	 * getCivilization():
	 * 
	 * @return return the civilization of the player
	 */
	public Civilization getCivilization() {
		return civ;
	}

	/**
	 * getCommandQueue():
	 * 
	 * @return returns the current queue of commands issued by the player.
	 */
	public CommandQueue getCommandQueue() {
		return cq;
	}

	/**
	 * getUpgrades()
	 * 
	 * @return returns current upgrades player has unlocked
	 */
	public Upgrades getUpgrades() {
		return upgrades;
	}

	/**
	 * addACtionTo(): Allows player to add an action to a unit that they own.
	 * 
	 * @param u
	 *            - unit to add action to
	 * @param a
	 *            - action to be added to u.
	 */
	public void addActionTo(Unit u, Action a) {
		cq.push(a, u);
	}

	/**
	 * getBuilding() Accesses buildings within player's objects, associated with
	 * given UUID.
	 * 
	 * @param buildingId
	 *            - UUID of the building we want to retrieve
	 * @return returns the building object with the UUID give.
	 */
	public Building getBuilding(UUID buildingId) {
		return objects.getBuildings().get(buildingId);
	}

	/**
	 * getUnit() Accesses unit within player's objects associated with given
	 * UUID
	 * 
	 * @param id2
	 *            - id of unit to access
	 * @return Unit abject associated to UUID given.
	 */
	public Unit getUnit(UUID id2) {
		return objects.getUnits().get(id2);
	}

	/**
	 * addBuilding() Adds a building to player's objects
	 * 
	 * @param b
	 *            - building to add
	 */
	public void addBuilding(Building b) {
		objects.addBuilding(b);
	}

	/**
	 * selectUnit() selects a Unit object. This will allow the player to move or
	 * control said unit. Does so by adding unit to player's selectedUnits map.
	 * 
	 * @param u
	 *            - unit to select
	 */
	public void selectUnit(Unit u) {
		selectedUnits.put(u.getId(), u);
	}

	/**
	 * selectBuilding() allows player to select a building, by adding building
	 * to player's selectedBuildings map.
	 * 
	 * @param b
	 *            - building to select
	 */
	public void selectBuilding(Building b) {
		selectedBuildings.put(b.getId(), b);
	}

	/**
	 * deselectBuilding(): deselects a specific Building that is currently
	 * selected by the player. does so by removing building from player's
	 * selectedBuildings map.
	 * 
	 * @param b
	 *            - building to deselect.
	 */
	public void deselectBuilding(Building b) {
		selectedBuildings.remove(b);
	}

	/**
	 * isSelectedBuilding() checks to see if a given building is selected.
	 * 
	 * @param b
	 *            - building to check
	 * @return true if building is selected, false if not
	 */
	public boolean isSelectedBuilding(Building b) {
		return selectedBuildings.containsKey(b);
	}

	/**
	 * isSelectedUnit checks to see if a given unit is selected
	 * 
	 * @param u
	 *            - unit to check
	 * @return true if unit is selected, false if not
	 */
	public boolean isSelectedUnit(Unit u) {
		return selectedUnits.containsKey(u);
	}

	/**
	 * deselect(): deselects all currently selected objects by player - units
	 * and buildings - does so by clearing contents of selectedBuildings and
	 * selectedUnits.
	 */
	public void deselect() {
		selectedBuildings = new HashMap<UUID, Building>();
		selectedUnits = new HashMap<UUID, Unit>();
	}

	// TODO implement the right way? Graham
	/**
	 * getGoodsNumber() returns number of goods associated with Given Goods
	 * object
	 * 
	 * @param g
	 *            - goods to get number of
	 * @return an integer of the number of goods for goods g
	 */
	public int getGoodsNumber(Goods g) {
		return goodsNumber.get(g).intValue();
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

	public boolean chargePlayerForUnitProduction(Resources productionCost) {

		return resources.spend(productionCost);

	}

	public void addResources(Resources r) {
		this.getResources().receive(r);
	}
}
