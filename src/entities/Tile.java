package entities;

import java.util.ArrayList;

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
	 * tentative variables : resource owner occupied by image terrain type
	 */

	// More than 1 unit
	ArrayList<Unit> unitsOccupying;

	private boolean passable = true;
	private Resource resource;
	private float height;

	public Tile(int resourceNum, float heightMap) {

		unitsOccupying = new ArrayList<Unit>();
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

	}

	public boolean isPassable() {
		return passable;
	}

	public void placeUnit(Unit u) {

		unitsOccupying.add(u);

	}
	
	public float getHeight() {
		return height;
	}

}
