package entities;

import entities.Resource;

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
	private int resourceAmount; // amount of resources tile has.
	private float resourceReg; // this tile's resource regen rate.
	
	private float height; // Height generated from the diamondSquareGenerator in
							// GameBoard
	private Player owner; // The Player that contains possession of the tile,
							// though mostly it will not have an ownder
	private Terrain terrain; // The type of terrain for this tile, based upne
								// height.
	private boolean occupiedByBuilding = false; // keeps track of whether or not
												// tile is occupied by buliding.

	// Resource Base Amounts - 'Average' amount a tile contiaining resource X will start with
	private static final int foodBase  = 200; 
	private static final int woodBase  = 100; 
	private static final int stoneBase = 300; 
	private static final int goldBase  = 500; 
	
	// Resource Regeneration rates - how much of resource will regenerate during each 'Tick'
	private static final float foodReg  = 1.100f; 
	private static final float woodReg  = 1.300f; 
	private static final float stoneReg = 1.050f; 
	private static final float goldReg  = 1.025f; 
	


	/*
	 * Tile(): 
	 * Description: Constructor for Tile Object. Determines the terrain
	 * type based on heightMap, and assigns resource.
	 * 
	 * Parameters:
	 * @param int resourceNum - the number of the resource, Currently 0 for
	 * Gold, 1 for wood, 2 for food, and 3 for stone
	 * 
	 * @param float heightMap - the height of this specific tile, determines the
	 * terrain type.
	 * 
	 * Return Value: 
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

		setResourceAmount(resource);
		setResourceRegen(resource);
		
		owner = null;
		passable = true;
		terrain = calculateTerrainType(height);
	}

	/*
	 * Tile(): 
	 * Description: Constructor for Tile Object. Determines the terrain
	 * type based on heightMap, and assigns resource.
	 * 
	 * Parameters:
	 * @param Resource r - the resource to assign (generally NONE for constructor)
	 * 
	 * @param float heightMap - the height of this specific tile, determines the
	 * terrain type.
	 * 
	 * Return Value: 
	 * @return a new Tile object.
	 */
	public Tile(Resource r, float heightMap) {
		height = heightMap;

		resource = r;
		
		setResourceAmount(resource); 
		setResourceRegen(resource);
		
		owner = null;
		passable = true;
		terrain = calculateTerrainType(height);
	}
	
	/*
	 * setResourceRegen():
	 * Description:
	 * Based on resource type, sets the regen rate
	 * 
	 * Parameters:
	 * @param Resource r - the resource to assign 
	 * 
	 * Return Value:
	 * @return void 
	 * 
	 * TODO add noise to regen amounts? 
	 */
	public void setResourceRegen(Resource r)
	{
		switch (r)
		{
			case NONE:
				this.resourceReg = 0f;
				break;
			case GOLD:
				this.resourceReg = goldReg;
				break;
			case STONE:
				this.resourceReg = stoneReg;
				break;
			case FOOD:
				this.resourceReg = foodReg;
				break;
			case WOOD:
				this.resourceReg = woodReg; 
				break;
			default:
				this.resourceReg = 0f;
				break;
		}
	}
	
	/*
	 * setResourceAmount():
	 * Description:
	 * Sets the base amount of a resource for a tile. Values based on 
	 * constants described at top of file.
	 * TODO in future add ability to fluctuate start vlaues. 
	 * 
	 * Parameters:
	 * @param Resource r - resource this tile will contain
	 * 
	 * Return value:
	 * @return the amount of resource this tile is set to. 
	 */
	public int setResourceAmount(Resource r)
	{
		// TODO add noise. fluctuate starting amounts
		switch (r)
		{
			case NONE:
				this.resourceAmount = 0;
				break;
			case GOLD:
				this.resourceAmount = goldBase;
				break;
			case STONE:
				this.resourceAmount = stoneBase;
				break;
			case FOOD:
				this.resourceAmount= foodBase;
				break;
			case WOOD:
				this.resourceAmount = woodBase; 
				break;
			default:
				this.resourceAmount = 0;
				break;
		}
		return this.resourceAmount; 
	}
	
	/*
	 * generateResource():
	 * Description:
	 * regenerates resource for given tile. Should be called once a 'Tick'
	 */
	public void generateResource()
	{
		this.resourceAmount *= this.resourceReg;
	}
	
	/*
	 * takeResource():
	 * Description:
	 * If current tile has a resource, allow player to take up to amount given. 
	 * TODO: possibly add parameter that is resource they are trying to take? 
	 * 
	 * Parameters:
	 * @param int amount - amount of resource unit/player is trying to take
	 * 
	 * Return value:
	 * @return amount taken; can be 0 - amount (inclusive). 
	 */
	public int takeResources(int amount)
	{
		int res;
		if (this.resource == Resource.NONE || this.resourceAmount == 0)
		{
			return 0; 
		} else if (this.resourceAmount < amount) 
		{
			res = this.resourceAmount;
			this.resourceAmount = 0;
			this.resourceReg = 0f;
			this.resource = Resource.NONE;
			return res; 
		} else
		{
			this.resourceAmount -= amount;
			return amount;
		}
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
		setResourceAmount(this.resource);
		setResourceRegen(this.resource);
		
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
			return Terrain.HILL;
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
