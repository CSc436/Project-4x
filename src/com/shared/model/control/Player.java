package com.shared.model.control;

import java.io.Serializable;
import java.util.Map;

import com.shared.model.buildings.Building;
import com.shared.model.entities.Civilization;
import com.shared.model.entities.PerfectCivilization;
import com.shared.model.entities.PlayerUnits;
import com.shared.model.research.TechnologyTree;
import com.shared.model.research.Upgrades;
import com.shared.model.resources.Resources;
import com.shared.model.units.Agent;
import com.shared.model.units.Unit;
import com.shared.model.units.agents.Goods;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426296427536759563L;
	private String name;
	private int id;
	public Map<Agent, Integer> prestige;
	public Map<Goods, Integer> goodsNumber;
	private PlayerUnits objects; // the objects that the player owns, including
									// the currently selected units
	private TechnologyTree techTree;
	private Upgrades upgrades;
	public Resources resources;
	public Goods goods;
	private Civilization civ;

	public Player() {
		this("", 0, new PerfectCivilization());
	}

	// Bare constructor
	public Player(String name, int id) {
		this(name, id, new PerfectCivilization());
	}

	public Player(String alias, int id, Civilization civ) {
		// Start player out with:
		// 1000 Gold, 1000 Wood, 1000 Food, 1000 Stone, 50 Research
		this(alias, id, civ, new Resources(1000, 1000, 1000, 1000, 50));
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

	public Resources getResources() {
		return resources;
	}

	public TechnologyTree getTechTree() {
		return techTree;
	}

	public Civilization getCivilization() {
		return civ;
	}

	public Upgrades getUpgrades() {
		return upgrades;
	}

	public Building getBuilding(int buildingId) {
		return objects.getBuildings().get(buildingId);
	}

	public Unit getUnit(int id2) {
		return objects.getUnits().get(id2);
	}

	public void addBuilding(Building b) {
		objects.addBuilding(b);
	}

	public int getGoodsNumber(Goods g) {
		return 1;
	}

	public void addUnit(Unit u) {
		objects.addUnit(u);

	}
	
	/**
	 * Checks if the player can build based on that building's resource cost
	 * 
	 * @param buildingResources - Resources of the BuildingType
	 * @return - boolean T/R if this player can build something based on that building's resource cost
	 */
	public boolean canBuild(Resources buildingResources) {
		if (resources.getFood() >= buildingResources.getFood() &&
				resources.getGold() >= buildingResources.getGold() &&
				resources.getResearchPts() >= buildingResources.getResearchPts() &&
				resources.getStone() >= buildingResources.getStone() &&
				resources.getWood() >= buildingResources.getWood()) {
			return true;
		}
		return false;
	}
}
