package entities.gameboard;

import java.util.ArrayList;
import java.util.Random;

import com.shared.Terrain;

import entities.buildings.Building;
import entities.buildings.BuildingType;
import entities.util.Point;

/**
 * Programmer : Ben Deininger & Nick Topping
 * 
 * Purpose : This class creates an instance of a game board, which is a 2d array
 * of Tile objects. A tile object will tentatively contain simple ownership and
 * a resource. There will be a collection of players for the board. The
 * constructor requires the row and column size and the number of players.
 * 
 */
public class GameBoard {

	private Tile[][] map;
	private int rows;
	private int cols;
	private static Random rand = new Random();
	private float averageHeight = 0f; // Stores the average height of the noise
									  // map.

	private static final float foodMult  = 0.02f;  // 5% of tiles that can
													// support food have food
	private static final float woodMult  = 0.15f;   // 5% of tiles that can support
													// wood have wood
	private static final float stoneMult = 0.050f;  // 5% of tiles that can
													// support stone have stone
	private static final float goldMult  = 0.010f;  // 0.2% of tiles that can
													// support gold have gold
	private int[] terrainCount = new int[6];		// stores the count of each resource tile type.
	
	private int massageEdge = 2; // used to create slightly larger board to massage map first. 
	/**
	 * GameBoard(): Description: Constructor for a game board object, requires
	 * the number of rows and columns for board dimensions - should be equal for
	 * best terrain results - and the number of players that will appear on the
	 * map (AI or Human). This constructor ensures that the resulting gameboard
	 * will not be a Hoth or Waterworld.
	 * 
	 * Parameters:
	 * 
	 * @param int row - number of rows the gameboard class will have
	 * 
	 * @param int col - number of columns the gameboard class will have
	 * 
	 * @param int numPlayers - number of players that will be on the gameboard.
	 * 
	 *        Notes: To complete a gameboard object, you will need to call a
	 *        resource distribution method and a player distribution method
	 *        (TODO implement a player dist method).
	 */
	public GameBoard(int row, int col) {
		rows = row;
		cols = col;
		map = new Tile[row + massageEdge][col + massageEdge];

		// create players with default "playerIDno" names

		// create a noisemap
		System.out.println("Building noisemap...");
		long startTime = System.currentTimeMillis();
		float[][] noisemap = diamondSquareGenerator(row,
				System.currentTimeMillis(), 0.1f);
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime));

		// create tiles
		// needs optimization!
		System.out.println("\nBuilding Board...");
		startTime = System.currentTimeMillis();

		// create a random number generator
		// Random rand = new Random();
		// rand.setSeed(16);

		System.out.println("Average Height: " + this.averageHeight);

		float heightAdjust = 0.0f;
		// Determine adjustment for height
		if (this.averageHeight < 0.5f) // If average height is less than .5, it
										// is a 'waterworld'
		{
			heightAdjust = 0.55f - this.averageHeight;
		} else if (this.averageHeight > 0.7f) // if average height is greater
												// than .7, it is a 'winter
												// wonderland'
		{
			heightAdjust = (0.7f - this.averageHeight - 0.05f); // will give us
																// a negative
																// height. to
																// lower some of
																// the terain.
																// (hopefully)
		}
		System.out.println("Height Adjust: " + heightAdjust);

		for (int r = 0; r < rows + massageEdge; r++) {
			for (int c = 0; c < cols + massageEdge; c++) {
				float height = noisemap[r][c];
				map[r][c] = new Tile(Resource.NONE, height + heightAdjust,
						(float) r, (float) c); // Use the adjusted height to
												// create the tile
				terrainCount[map[r][c].getTerrainType().getValue() + 2]++; // increment counts
				//[0] = Forest, [1] = Water, [2] = Sand, [3] = Grass, [4] = mountain, [5] = snow.
			}
		}
		
		// massageTerrain so that it will produce a visually pleasing map
		massageTerrain();
		
		// Trim off the edges of map, gets rid of left over noise from massage.
		trimEdges();
		
		// TODO distribute players
		// Based on num players
		// 1 - place player roughly in center
		// 2 - place players caddy corner
		// 3 + 4 - place players in corners of map
		// 5 - 1-4 place in corner, 5 place in center.
		// attempt to distribute near resources.

		endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime)
				+ "\n");

	}

	/**
	 * trimEdges()
	 * After massaging the terrain, need to trim off the edges.
	 */
	private void trimEdges()
	{
		Tile [][] newMap = new Tile [rows][cols];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				newMap[i][j] = map[i+1][j+1];
			}
		}
		map = newMap;
	}
	
	
	/**
	 * massageTerrain(): 
	 * Description: 
	 * Makes sure that terrain generated will produce a visually 
	 * pleasing map when tiles are used. Called from the constructor, should
	 * not be called by user.  
	 */
	private void massageTerrain()
	{
		System.out.println("Massaging Terrain...");
		// make less random
		mtHomogenize(4); 
		
		// Loner tile removal should follow same format as mtHomogenize, only 
		// check to see if count of type of terrain of curr tile is only 1. 
			// add another chekc to mtHomogenize, allow it to see if only 1 of current tile type, if so change to most regardless 

		// Transition Analysis 
		mtShoreAnalysis(Terrain.WATER, Terrain.SAND, Terrain.SAND);
		mtShoreAnalysis(Terrain.SAND, Terrain.WATER, Terrain.GRASS);
		mtShoreAnalysis(Terrain.GRASS, Terrain.SAND, Terrain.FOREST);
		mtShoreAnalysis(Terrain.FOREST, Terrain.GRASS, Terrain.MOUNTAIN);
		mtShoreAnalysis(Terrain.MOUNTAIN, Terrain.FOREST, Terrain.SNOW);
		mtShoreAnalysis(Terrain.SNOW, Terrain.MOUNTAIN, Terrain.SNOW);
		// do diagonal analysis
		
		System.out.println("Terrain Massage Complete!");
	}
	
	/**
	 * mtHomogenize():
	 * Used to homogenize terrain, make it less random. 
	 * @param buf - number of tiles of one type that must appear around tile
	 *  to change it
	 */
	private void mtHomogenize(int buf)
	{
		// Pass 1: Make terrain more homogeneous. Remove the noise a bit.
		int maxCount;
		for (int m = 0; m < 4; m++)
		{
			for (int i = 1; i < map.length - 1; i++)
			{
				for (int j = 1; j < map[i].length - 1; j++)
				{
					int tCount[] = new int [6]; // counts occurrences of terrain around a tile.
					
					// look at surrounding tiles
					for (int k = i-1; k < i+2; k++)
					{
						for (int l = j-1; l < j+2; l++)
						{
							tCount[map[k][l].getTerrainType().ordinal()]++;
						}
					}
					
					maxCount = 0;
					
					// Determine which tile occurrs the most. set center to that kind.
					for (int k = 1; k < tCount.length; k++)
					{
						if (tCount[k] > tCount[maxCount])
						{
							maxCount = k; 
						}
					}
					// Change middle tile terrain
					if (tCount[map[i][j].getTerrainType().ordinal()] == 1)
					{
						// if current tile is surrounded by no similar tiles. 
						// change to most common. TODO possibly make it 2, not 1
						map[i][j].setTerrainType(Terrain.values()[maxCount]);
						
					}
					else if (tCount[maxCount] > buf)
					{
						map[i][j].setTerrainType(Terrain.values()[maxCount]);
					}
				}
			}
		}
	}

	/**
	 * mtShoreAnalysis():
	 * This function makes sure that water tiles are either surrounded
	 * by water tiles, or Dirt. Initially there are some water-grass shores
	 * which does not work with our current tile set. 
	 * 
	 * @param t1, terrain to analyze
	 * @param t2, terrain this should transition to 
	 * @param t3, terrain this should transition to 
	 * 
	 * TODO need to do for all transitions. 
	 */
	private void mtShoreAnalysis(Terrain t1, Terrain t2, Terrain t3)
	{
		//int wCount;
		for (int i = 1; i < map.length - 1; i++)
		{
			for (int j = 1; j < map[i].length - 1; j++)
			{
				// If terrain type is water, make sure it is surrounded by water
				// or dirt. Perhaps if fewer than X number of tiles are water, then
				// change to dirt. 
				//wCount = 0;
				if (map[i][j].getTerrainType() == t1)
				{
					for (int k = i-1; k < i+2; k++)
					{
						for (int l = j-1; l < j+2; l++)
						{
							// If tile not a matching transition, switch to transition tile
							if (map[k][l].getTerrainType() != t1 && 
								map[k][l].getTerrainType() != t2 &&
								map[k][l].getTerrainType() != t3)
							{
								map[k][l].setTerrainType(t3);
							}
						}
					}
				}
			}
		}
	}
	
	/** @deprecated use resourceDistNat instead
	 * resourceDistNatural(): Description: Default resource distribution, uses
	 * constants at top of file. Allocates tile types into a variety of
	 * linkedlists, attempts to perform 'realistic' resource distribution
	 */
	public void resourceDistNatural() {
		ArrayList<ArrayList<Point>> terrainList = new ArrayList<ArrayList<Point>>();
		// Init lists for terain types
		for (int i = 0; i < 6; i++) {
			terrainList.add(new ArrayList<Point>());
		}

		// Add different tiles to lists.
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				terrainList.get(map[i][j].getTerrainType().ordinal()).add(
						new Point(i, j));
			}
		}

		System.out.println("Distributing resources in Natural Fashion...");
		System.out.println("Distributing food...");
		int numFood = (int) (foodMult * (terrainList.get(
				Terrain.WATER.ordinal()).size()
				+ terrainList.get(Terrain.SAND.ordinal()).size() + terrainList
				.get(Terrain.GRASS.ordinal()).size()));
		// food can exist in lists 0,1,2
		terrainList = resourceDistNaturalHelp(terrainList, numFood,
				Resource.FOOD, 0, 3);

		System.out.println("Distributing wood...");
		int numWood = (int) (woodMult * (terrainList.get(
				Terrain.GRASS.ordinal()).size() + terrainList.get(
				Terrain.FOREST.ordinal()).size()));
		terrainList = resourceDistNaturalHelp(terrainList, numWood,
				Resource.WOOD, 2, 2);
		// Wood can exist in lists 2 and 3

		System.out.println("Distributing stone...");
		int numStone = (int) (stoneMult * (terrainList.get(
				Terrain.FOREST.ordinal()).size()
				+ terrainList.get(Terrain.MOUNTAIN.ordinal()).size() + terrainList
				.get(Terrain.SNOW.ordinal()).size()));
		terrainList = resourceDistNaturalHelp(terrainList, numStone,
				Resource.STONE, 3, 3);

		System.out.println("Distributing gold...");
		int numGold = (int) (goldMult * (terrainList.get(
				Terrain.FOREST.ordinal()).size()
				+ terrainList.get(Terrain.MOUNTAIN.ordinal()).size() + terrainList
				.get(Terrain.SNOW.ordinal()).size()));
		terrainList = resourceDistNaturalHelp(terrainList, numGold,
				Resource.GOLD, 3, 3);
	}

	/** @deprecated use resourceDistNat
	 * resourceDistNaturalHelp(): Description: Distributes one given resource.
	 * 
	 * Parameters:
	 * 
	 * @param terrainList
	 *            - list of available terrain tiles that can have resources
	 * 
	 * @param numRes
	 *            - number of specific resource to distribute
	 * 
	 * @param res
	 *            - resource to distribute
	 * 
	 * @param offset
	 *            - offset in terrainList, first list this resource can appear
	 *            in
	 * 
	 * @param numLists
	 *            - number of lists this resource can appear in.
	 * 
	 *            Return Value:
	 * 
	 * @retrun the altered terrainList
	 */
	private ArrayList<ArrayList<Point>> resourceDistNaturalHelp(
			ArrayList<ArrayList<Point>> terrainList, int numRes, Resource res,
			int offset, int numLists) {
		if (terrainList.size() == 0)
			return terrainList;

		Point temp;
		Random rnd = new Random();
		int r, rp, rpp;
		while (numRes > 0) {
			r = Math.abs(rnd.nextInt());
			rp = offset + r % numLists;
			if (terrainList.get(rp).size() == 0) {
				continue;
			}
			rpp = r % terrainList.get(rp).size();
			do {
				if (terrainList.get(rp).size() == 0) {
					break;
				}
				temp = terrainList.get(rp)
						.get(rpp % terrainList.get(rp).size());
				map[Math.round(temp.x)][Math.round(temp.y)].setResource(res);
				terrainList.get(rp).remove(rpp % terrainList.get(rp).size());
				numRes--;
			} while (rnd.nextBoolean());
		}
		return terrainList;
	}

	/**
	 * resourceDistNat():
	 * Description:
	 * New version of natural resource distribution, not so liney with the resource placement. 
	 * doesn't place food resources on water like resourceDistNatural
	 */
	public void resourceDistNat()
	{
		int r,c;
		
		// determine tiles that can support resources, number of which will be placed for each resource
		int foodCount  = (int) ((terrainCount[2] + terrainCount[3] + terrainCount[0]) * foodMult); // food placed on dirt, grass, and forest. 
		int stoneCount = (int) ((terrainCount[4] + terrainCount[5] + terrainCount[0]) * stoneMult); // stone can go on forest, mountain, snow.
		int woodCount  = (int) ((terrainCount[0] + terrainCount[4]) * woodMult); // wood can go in forest and mountain
		int goldCount  = (int) ((terrainCount[4] + terrainCount[5]) * goldMult); // gold can go on mountain and snow. 

		// start picking points at random, randomly choose valid resource to place on tile
		System.out.println("resourceDistNat: Distributing food resources...");
		while (foodCount > 0)
		{
			// pick tile, see if resource and valid. 
			r = rand.nextInt(rows);
			c = rand.nextInt(cols);
			if (map[r][c].getResource() == Resource.NONE && (map[r][c].getTerrainType() == Terrain.SAND || 
			    map[r][c].getTerrainType() == Terrain.GRASS || map[r][c].getTerrainType() == Terrain.FOREST))
			{
				map[r][c].setResource(Resource.FOOD);
				foodCount -= (resourceClump(r, c, 2, Resource.FOOD) + 1);
			} else
			{
				continue;
			}	
		}
		System.out.println("resourceDistNat: Distributing stone resources...");
		while (stoneCount > 0)
		{
			// pick tile, see if resource and valid. 
			r = rand.nextInt(rows);
			c = rand.nextInt(cols);
			if (map[r][c].getResource() == Resource.NONE && (map[r][c].getTerrainType() == Terrain.MOUNTAIN || 
			    map[r][c].getTerrainType() == Terrain.SNOW || map[r][c].getTerrainType() == Terrain.FOREST))
			{
				map[r][c].setResource(Resource.STONE);
				stoneCount -= (resourceClump(r, c, 3, Resource.STONE) + 1);
			} else
			{
				continue;
			}
		}
		System.out.println("resourceDistNat: Distributing wood resources...");
		while (woodCount > 0)
		{
			// pick tile, see if resource and valid. 
			r = rand.nextInt(rows);
			c = rand.nextInt(cols);
			if (map[r][c].getResource() == Resource.NONE &&
			    (map[r][c].getTerrainType() == Terrain.MOUNTAIN || map[r][c].getTerrainType() == Terrain.FOREST))
			{
				map[r][c].setResource(Resource.WOOD);
				woodCount -= (resourceClump(r, c, 10, Resource.WOOD) + 1);
				// Attempt clumping 
			} else
			{
				continue;
			}
		}
		System.out.println("resourceDistNat: Distributing gold resources...");
		while (goldCount > 0)
		{
			// pick tile, see if resource and valid. 
			r = rand.nextInt(rows);
			c = rand.nextInt(cols);
			if (map[r][c].getResource() == Resource.NONE && (map[r][c].getTerrainType() == Terrain.MOUNTAIN || 
			    map[r][c].getTerrainType() == Terrain.SNOW))
			{
				map[r][c].setResource(Resource.GOLD);
				goldCount -= (resourceClump(r, c, 1, Resource.GOLD) + 1);
				// Attempt clumping 
			} else
			{
				continue;
			}
		}
		System.out.println("\nresourceDistNat: Done distributing resources!\n");
	}
	
	/**
	 * resourceClump()
	 * Helper method used to clump resources, spirals out from source. 
	 * 
	 * @param r - source location row
	 * @param c - source location column 
	 * @param clump - how many chances clumper can fail. (random boolean return false x number of times)
	 * @param res - resource to play 
	 * @return number of resources placed. 
	 * 
	 */
	private int resourceClump(int r, int c, int clump, Resource res)
	{
		int count = 0; 
		int d1, d2; // values to add to current positions (r and c) -1 to 1. 
		
		while (clump >= 0)
		{
			d1 = (int) ((rand.nextInt() % 131) % 2 - 2); // come up with better way, get rid of diagonal. 
			d2 = (int) ((rand.nextInt() % 131) % 2 - 2); 
			r +=  d1; 
			c +=  d2; 
			if (r >= 0 && r < rows && c >= 0 && c < cols) // check within bounds
			{
				if (map[r][c].getTerrainType() == Terrain.WATER)
				{
					break;
				}
				if (map[r][c].getResource() == Resource.NONE)
				{
					if (rand.nextBoolean())
					{
						count++; // increment number of resources placed
						map[r][c].setResource(res); // set resource
					} else // failed to place, decrement clump
					{
						clump--;
					}
				} else 
				{
					//System.out.println("Has resource");
					continue;
				}
			} else  // out of bounds of map, break loop. 
			{
				break;
			}
		}
		
		return count;
	}

	/**
	 * getRows(): Description: returns the number of rows this gameboard object
	 * has
	 * 
	 * Return Value:
	 * 
	 * @return an int - the number of rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * getCols(): Description: Returns the number of columns a gameboard object
	 * has
	 * 
	 * Return Value:
	 * 
	 * @return an int - the number of columns
	 */
	public int getCols() {
		return cols;

	}

	/**
	 * getTileAt(): Description: Returns a Tile object at the given location.
	 * Note: this function does NOT currently check to make sure that the
	 * coordinates are within the bounds of the board.
	 * 
	 * Parameters:
	 * 
	 * @param int x - x location of tile (0 to numRows - 1)
	 * 
	 * @param int y - y location of tile (0 to numCols - 1)
	 * 
	 *        Return Value:
	 * 
	 * @return Tile object at location x,y
	 */
	public Tile getTileAt(int x, int y) {
		return map[x][y];
	}

	/**
	 * canPlaceBuildingAt(): returns true if a building can be placed at the tile of the given coordinate 
	 * @param buildingType - type of building to place
	 * @param x - x coordinate of tile
	 * @param y - y coordinate of tile
	 * @return true if building can be placed, false if not. 
	 */
	public boolean canPlaceBuildingAt(BuildingType buildingType, int x, int y) {

		int dc = (cols - (x + buildingType.getX() - 1));
		int dr = (rows - (y + buildingType.getY() - 1));


		boolean boo = false;
		// if the placement of the building does not go off the map if (dc > 0
		// && dr > 0) {
		if (dc > 0 && dr > 0) {

			// set the area of the rectangle to be occupied by a building

			for (int r = x; r < (x + buildingType.getX()); r++) {

				for (int c = y; c < (y + buildingType.getY()); c++) {
					if (map[r][c].isOccupiedByBuilding()) {
						boo = false;
						break;

					} else {

						boo = true;

					}
				}
			}

			return boo;
		} else {
			return false;
		}

	}

	/**
	 * placeBuildingAt(): Description: Places a building object at the location
	 * (x,y) on the map.
	 * 
	 * Parameters:
	 * 
	 * @param Building
	 *            b - building to place on map
	 * 
	 * @param int x - x coordinate where to place building (0 to rows - 1)
	 * 
	 * @param int y - y coordinate where to place building (0 to cols - 1)
	 */
	public boolean placeBuildingAt(Building b, int x, int y) {

		// the tile must be owned to place a building

		// calculate the distance to the the bottom right corner
		int dc = (cols - (x + b.getWidth() - 1));
		int dr = (rows - (y + b.getHeight() - 1));


		boolean boo = false;
		// if the placement of the building does not go off the map if (dc > 0
		// && dr > 0) {
		if (dc > 0 && dr > 0) {

			// set the area of the rectangle to be occupied by a building

			for (int r = x; r < (x + b.getHeight()); r++) {

				for (int c = y; c < (y + b.getWidth()); c++) {
					if (map[r][c].isOccupiedByBuilding()) {
						boo = false;
						break;

					} else {
						map[r][c].setIsOccupiedByBuilding(true);
						boo = true;

					}
				}
			}

			return boo;
		} else {
			return false;
		}

	}

	/**
	 * removeBuilding(): Description: removes a given building from the board
	 * and sets the tiles that were occupied by the building to vacant. Note:
	 * Building must be within range
	 * 
	 * Parameters:
	 * 
	 * @param Building
	 *            b - building to remove from this gameboard.
	 */
	public void removeBuilding(Building b) {

		Point pos = b.getPosition();
		int x = Math.round(pos.x);
		int y = Math.round(pos.y);

		int height = b.getHeight();
		int width = b.getWidth();

		for (int r = x; r < (x + height); r++) {

			for (int c = y; c < (y + width); c++) {
				map[r][c].setIsOccupiedByBuilding(false);
			}
		}
	}

	/**
	 * Gabe Kishi - DiamondSquare Procedurally generates terrain using a height
	 * map produced by the Diamond Squares algorithm
	 * http://en.wikipedia.org/wiki/Diamond-square_algorithm
	 */

	// Note: removed 'static' modifier
	private float[][] diamondSquareGenerator(int SIZE, long seed,
			float roughness) {
		// size is the nearest power of 2 that fully contains SIZE plus 1
		int size = (1 << (int) Math.ceil(Math.log(SIZE) / Math.log(2))) + 1;

		// seed random number generator
		rand.setSeed(seed);
		float[][] noise = new float[size][size];

		System.out.println("SEED : " + seed);

		// initialize the corner values
		noise[0][0] = rand.nextFloat();
		noise[size - 1][0] = rand.nextFloat();
		noise[0][size - 1] = rand.nextFloat();
		noise[size - 1][size - 1] = rand.nextFloat();

		// begin Diamond Squares iteration
		dsIter(noise, size, roughness);

		// TODO possibly inline with dsIter, but for now iterate through and
		// calculate averageheight
		for (int x = 0; x < noise.length; x++) {
			for (int y = 0; y < noise[x].length; y++) {
				this.averageHeight += noise[x][y];
			}
		}

		// make averageHeight the average (not just the sum)
		this.averageHeight = averageHeight / (noise.length * noise[0].length);

		return noise;
	}

	// Note: Removed 'static' modifier
	private void dsIter(float[][] noise, int size, float roughness) {
		int sideLength = size - 1;
		int sizeMinus = size - 1;
		float rough = roughness, avg;

		while (sideLength > 1) {
			int halfLen = sideLength / 2;

			// squares
			for (int x = 0; x < size - 1; x += sideLength)
				for (int y = 0; y < size - 1; y += sideLength) {
					avg = (noise[x][y] + noise[x + sideLength][y]
							+ noise[x][y + sideLength] + noise[x + sideLength][y
							+ sideLength]) / 4.0f;
					avg += 2 * rough * rand.nextFloat() - rough;
					noise[x + halfLen][y + halfLen] = avg;
				}

			float N, E, S, W;

			// diamonds

			for (int x = 0; x < size - 1; x += halfLen)
				for (int y = (x + halfLen) % sideLength; y < size - 1; y += sideLength) {
					W = noise[(x - halfLen + sizeMinus) % sizeMinus][y];
					E = noise[(x + halfLen) % sizeMinus][y];
					N = noise[x][(y - halfLen + sizeMinus) % sizeMinus];
					S = noise[x][(y + halfLen) % sizeMinus];

					avg = (N + E + S + W) / 4.0f;
					avg += 2 * rough * rand.nextFloat() - rough;
					noise[x][y] = avg;
					if (x == 0)
						noise[size - 1][y] = avg;
					if (y == 0)
						noise[x][size - 1] = avg;
				}

			sideLength /= 2;
			rough *= .8;
		}
	}

}
