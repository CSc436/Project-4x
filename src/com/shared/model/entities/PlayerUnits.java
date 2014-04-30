package com.shared.model.entities;


import java.io.Serializable;
import java.util.HashMap;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.ResourceBuilding;
import com.shared.model.units.Agent;
import com.shared.model.units.Unit;

public class PlayerUnits implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3305847733108811659L;
	// change to hashmaps, id->object
	private HashMap<Integer, GameObject> gameObjects;
	private HashMap<Integer, Building> buildings;
	private HashMap<Integer, ResourceBuilding> resourceBuildings;
	private HashMap<Integer, Unit> units;
	private HashMap<Integer, Agent> agents;
	private int playerId;
	
	public PlayerUnits() {}

	public PlayerUnits(int id) {
		playerId = id;
		gameObjects = new HashMap<Integer, GameObject>();
		units = new HashMap<Integer, Unit>();
		buildings = new HashMap<Integer, Building>();
		resourceBuildings = new HashMap<Integer, ResourceBuilding>();
		agents = new HashMap<Integer, Agent>();
	}

	public HashMap<Integer, GameObject> getGameObjects() {
		return gameObjects;
	}

	public HashMap<Integer, Building> getBuildings() {
		return buildings;
	}

	public HashMap<Integer, ResourceBuilding> getResourceBuildings() {
		return resourceBuildings;
	}

	public HashMap<Integer, Unit> getUnits() {
		return units;
	}

	public HashMap<Integer, Agent> getAgents() {
		return agents;
	}

	public void addGameObject(GameObject gameObject) {
		gameObjects.put(gameObject.getId(), gameObject);
	}

	public void addUnit(Unit unit) {
		units.put(unit.getId(), unit);
	}

	public void addBuilding(Building building) {
		buildings.put(building.getId(), building);
		addGameObject(building);
	}

	public void addAgent(Agent a) {
		agents.put(a.getId(), a);
		addUnit(a);
	}

	public GameObject removeGameObject(Integer id) {
		return gameObjects.remove(id);
	}

	public Building removeBuilding(Integer id) {
		removeGameObject(id);
		return buildings.remove(id);
	}

	public ResourceBuilding removeResourceBuilding(Integer id) {
		removeGameObject(id);
		removeBuilding(id);
		return resourceBuildings.remove(id);
	}

	public Unit removeUnit(Integer id) {
		return units.remove(id);
	}

	public Agent removeAgent(Integer id) {
		units.remove(id);
		return agents.remove(id);
	}

	@Override
	public String toString() {
		String returnStr = "Player " + playerId + "'s gameObjects:\n";
		for (Integer id : gameObjects.keySet()) {
			returnStr += gameObjects.get(id).toString();// responsibility of
														// leaf class to write
														// out one line toString
		}

		returnStr += "\n";
		return returnStr;
	}


}
