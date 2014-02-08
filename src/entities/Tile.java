package entities;

import java.util.ArrayList;

import entities.units.Unit;

public class Tile {

	/*
	 * Programmer: Benjamin Deininger
	 * 
	 * Purpose: This class defines a tile. The gameboard is made up of a 2d
	 * array of tiles. Each tile will have a passable boolean, a resource, and a
	 * list of units occupying the tile at any given time.
	 * 
	 * 
	 * 
	 * 
	 * tentative variables :
	 */

	private boolean passable = true;
	private Resource resource;
	private float height;
	private Player owner;
	private Terrain type;

	private boolean occupiedByBuilding = false;

	// private float resource regenerate rate
	// private int resource number

	public Tile(int resourceNum, float heightMap) {

		height = heightMap;

		if (resourceNum == 0) {

			resource = Resource.GOLD;
		} else if (resourceNum == 1) {

			resource = Resource.WOOD;
		} else if (resourceNum == 2) {

			resource = Resource.FOOD;
		} else {

			resource = Resource.STONE;
		}

		calculateTerrainType();

	}

	public boolean isOccupiedByBuilding() {
		return occupiedByBuilding;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(Boolean b) {
		passable = b;
	}

	public Resource getResource() {
		return resource;
	}

	public float getHeight() {
		return height;
	}

	public void setOwner(Player p) {
		owner = p;

	}

	public Player getOwner() {
		return owner;
	}

	public boolean hasOwner() {
		if (owner == null)
			return false;
		else
			return true;
	}

	/*
	 * either find good seed values or tweak the algorithm to not generate pure
	 * water 1391895502278
	 */

	public void calculateTerrainType() {

		float newHeight = 255 * height;

		if (newHeight < 132) {
			type = Terrain.WATER;
		} else if (newHeight < 136) {
			type = Terrain.DIRT;
		} else if (newHeight < 140) {
			type = Terrain.GRASS;
		} else if (newHeight < 156) {
			type = Terrain.HILL;
		} else if (newHeight < 184) {
			type = Terrain.MOUNTAIN;
		} else {
			type = Terrain.SNOW;
		}

	}

	public Terrain getTerrainType() {
		return type;
	}
}
