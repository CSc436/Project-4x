package entities.gameboard;

import control.Player;

/**
 * 
 * @author Benjamin Deininger, Travis Strattom, and Nicholas Topping
 * @class Tile
 * @summary Tile class is the basic game space that units and buildings will
 *          populate. Tiles can be of different terrain types (see
 *          Terrain.java), contain various resources (see Resource.java), impede
 *          the movement of units, etc. The Tile class is used to populate a
 *          GameBoard object, in order to create the field of play for a 4X
 *          Game.
 */
public class Tile {

	private boolean passable; // Boolean representing whether or not units can
								// pass over/through a terrain tile.
	private Resource resource; // Resource that populates this tile.
	private float height; // Height generated from the diamondSquareGenerator in
							// GameBoard
	private Player owner; // The Player that contains possession of the tile,
							// though mostly it will not have an ownder
	private Terrain terrain; // The type of terrain for this tile, based upne
								// height.
	private boolean occupiedByBuilding = false; // keeps track of whether or not
												// tile is occupied by buliding.

	// TODO add private int resource amount;

	// private float regenerate

	/*
	 * Tile(): Description: Constructor for Tile Object. Determines the terrain
	 * type based on heightMap, and assigns resource.
	 * 
	 * Parameters:
	 * 
	 * @param int resourceNum - the number of the resource, Currently 0 for
	 * Gold, 1 for wood, 2 for food, and 3 for stone
	 * 
	 * @param float heightMap - the height of this specific tile, determines the
	 * terrain type.
	 * 
	 * Return Value:
	 * 
	 * @return a new Tile object.
	 */
	public Tile(int resourceNum, float heightMap) {
		height = heightMap;

		if (resourceNum == 0) {
			resource = Resource.GOLD;
		} else if (resourceNum == 1) {
			resource = Resource.WOOD;
		} else if (resourceNum == 2) {
			resource = Resource.FOOD;
		} else if (resourceNum == 3) {
			resource = Resource.STONE;
		} else {
			resource = Resource.NONE;
		}

		// TODO add 'determine resource amount' function
		// TODO based on resource type set resource regeneration

		owner = null;
		passable = true;
		terrain = calculateTerrainType(height);
	}

	/*
	 * Tile(): Description: Constructor for Tile Object. Determines the terrain
	 * type based on heightMap, and assigns resource.
	 * 
	 * Parameters:
	 * 
	 * @param Resource r - the resource to assign (generally NONE for
	 * constructor)
	 * 
	 * @param float heightMap - the height of this specific tile, determines the
	 * terrain type.
	 * 
	 * Return Value:
	 * 
	 * @return a new Tile object.
	 */
	public Tile(Resource r, float heightMap) {
		height = heightMap;

		resource = r;

		// TODO add 'determine resource amount' function
		// TODO based on resource type set resource regeneration

		owner = null;
		passable = true;
		terrain = calculateTerrainType(height);
	}

	// TODO add 'determine resource amount' function
	public int determineResourceAmount(Resource r) {
		return 0;
	}

	// TODO add 'Generate resource' function
	public void generateResource() {
		// based on regenerate float generate new value
		// set current value
	}

	// TODO add 'remove resource amount' function - return int of # resources
	// gathered
	public int takeResources(int amount) {
		// TODO return maximum amount that can be returned
		return amount;
	}

	/*
	 * isOccupiedByBuilding(): Description: returns whether or not tile is
	 * occupied by buliding
	 * 
	 * Return Value:
	 * 
	 * @return boolean value; true if occupied, false if not.
	 */
	public boolean isOccupiedByBuilding() {
		return occupiedByBuilding;
	}

	/*
	 * isPassable() Description: Returns whether or not this tile is passable by
	 * a unit. I.E. if the tile contains a wall/natural wall, then it will not
	 * be passable.
	 * 
	 * Return Value:
	 * 
	 * @return a boolean; true if passable, false if not.
	 */
	public boolean isPassable() {
		return passable;
	}

	/*
	 * setPassable(): Description: Sets the passable value of the tile. I.E. If
	 * player places a building or wall on a tile, it will become impassable.
	 * 
	 * Return Value:
	 * 
	 * @return void.
	 */
	public void setPassable(boolean b) {
		passable = b;
	}

	/*
	 * getResource(): Description: Returns the type of resource located on this
	 * tile.
	 * 
	 * Return Value:
	 * 
	 * @return REesource enum, refer to Resource.java for possible types.
	 */
	public Resource getResource() {
		return resource;
	}

	/*
	 * setResource(): Description: Sets the resource of the tile, only possible
	 * if tile is passable and contains no resource.
	 * 
	 * Parameters:
	 * 
	 * @param Resource r - type of resource to set tile to
	 * 
	 * Return Value:
	 * 
	 * @return a boolean value; true if possible to set resource, false if not.
	 * If false returned, Tile's resource was not set to new value.
	 */
	public boolean setResource(Resource r) {
		if (!this.passable || this.resource != Resource.NONE) {
			return false;
		}
		this.resource = r;
		return true;
	}

	/*
	 * getHeight(): Description: Returns the height value of this tile, which
	 * was generated by the diamondSquareGenerator method in GameBoard
	 * 
	 * Return Value:
	 * 
	 * @return the height (float) value.
	 */
	public float getHeight() {
		return height;
	}

	/*
	 * setOwner() Description: Sets the owner of this tile, i.e. if a player
	 * places a structure on a tile, it becomes their's. Can only set owner if
	 * setting to null, p is equal to current owner, or current owner is null.
	 * 
	 * Parameters:
	 * 
	 * @param Player p - Player who is new owner OR null.
	 * 
	 * Return Value:
	 * 
	 * @return boolean value; true if successfully set player, false if not
	 */
	public boolean setOwner(Player p) {
		if (p == null) {
			owner = p;
			return true;
		} else if (owner != null && !owner.equals(p)) {
			return false; // currently has an owner, that is not p.
		} else {
			owner = p; // tile is null, XOR owner == p.
			return true;
		}

	}

	/*
	 * getOwner() Description: Returns the current owner of the tile
	 * 
	 * Return Value:
	 * 
	 * @return Player object that is the current owner (may be null)
	 */
	public Player getOwner() {

		return owner;
	}

	/*
	 * hasOwner(): Description: Returns whether or not the current tile has an
	 * owner.
	 * 
	 * Return Value:
	 * 
	 * @return a boolean value; false if tile does not have an owner, true if it
	 * does.
	 */
	public boolean hasOwner() {
		if (owner == null)
			return false;
		else
			return true;
	}

	/*
	 * 1391895502278 - good seed for terrain generation.
	 */

	/*
	 * calculateTerrainType(): Description: Determines the terrain type based on
	 * the height value passed to the function. Currently can generate Water,
	 * Dirt, Grass, Hull, Mountain, and Snow terrain. TODO might be good to add
	 * some sort of safeguard to protect against 'WaterWorld' scenarios.
	 * 
	 * Parameters:
	 * 
	 * @param float height - height of terrain to generate
	 * 
	 * Return Value:
	 * 
	 * @return Terrain type that correspondes with given height.
	 */
	public Terrain calculateTerrainType(float height) {

		float newHeight = 255 * height;

		if (newHeight < 132) {
			return Terrain.WATER;
		} else if (newHeight < 136) {
			return Terrain.DIRT;
		} else if (newHeight < 140 + 5) {
			return Terrain.GRASS;
		} else if (newHeight < 156 + 10) {
			return Terrain.FOREST;
		} else if (newHeight < 184 + 5) {
			return Terrain.MOUNTAIN;
		} else {
			return Terrain.SNOW;
		}
	}

	/*
	 * getTerrainType(): Description: Returns the terrain type of this tile.
	 * 
	 * Return Value:
	 * 
	 * @return current value of terrain.
	 */
	public Terrain getTerrainType() {
		return terrain;
	}

	public void setIsOccupiedByBuilding(boolean t) {
		occupiedByBuilding = t;
	}
}
