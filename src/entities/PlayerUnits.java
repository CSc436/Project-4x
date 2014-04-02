package entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.units.Agent;
import entities.units.Unit;

public class PlayerUnits {
	// change to hashmaps, id->object
	private HashMap<UUID, GameObject> gameObjects;
	private HashMap<UUID, Building> buildings;
	private HashMap<UUID, ResourceBuilding> resourceBuildings;
	private HashMap<UUID, Unit> units;
	private HashMap<UUID, Agent> agents;
	private int playerId;

	public PlayerUnits(int id) {
		playerId = id;
		gameObjects = new HashMap<UUID, GameObject>();
		units = new HashMap<UUID, Unit>();
		buildings = new HashMap<UUID, Building>();
		resourceBuildings = new HashMap<UUID, ResourceBuilding>();
		agents = new HashMap<UUID, Agent>();
	}

	public Map<UUID, GameObject> getGameObjects() {
		return gameObjects;
	}

	public Map<UUID, Building> getBuildings() {
		return buildings;
	}

	public Map<UUID, ResourceBuilding> getResourceBuildings() {
		return resourceBuildings;
	}

	public Map<UUID, Unit> getUnits() {
		return units;
	}

	public Map<UUID, Agent> getAgents() {
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

	public GameObject removeGameObject(UUID id) {
		return gameObjects.remove(id);
	}

	public Building removeBuilding(UUID id) {
		removeGameObject(id);
		return buildings.remove(id);
	}

	public ResourceBuilding removeResourceBuilding(UUID id) {
		removeGameObject(id);
		removeBuilding(id);
		return resourceBuildings.remove(id);
	}

	public Unit removeUnit(UUID id) {
		return units.remove(id);
	}

	public Agent removeAgent(UUID id) {
		units.remove(id);
		return agents.remove(id);
	}

	@Override
	public String toString() {
		String returnStr = "Player " + playerId + "'s gameObjects:\n";
		for (UUID id : gameObjects.keySet()) {
			returnStr += gameObjects.get(id).toString();// responsibility of
														// leaf class to write
														// out one line toString
		}

		returnStr += "\n";
		return returnStr;
	}

	// public boolean hasUnitAt(int x, int y) {
	// for (int c = 0; c < units.size(); c++) {
	// if (units.get(c).getX() == x && units.get(c).getY() == y)
	// return true;
	// }
	// return false;
	// }

	// public boolean hasBuildingAt(int x, int y) {
	// for (int c = 0; c < buildings.size(); c++) {
	// if (buildings.get(c).getX() == x && buildings.get(c).getY() == y)
	// return true;
	// }
	// return false;
	// }
}
