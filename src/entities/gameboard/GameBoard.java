package entities.gameboard;

import java.util.ArrayList;
import java.util.Random;

import entities.buildings.Building;
import entities.units.Unit;
import entities.util.Point;

/* 
 *  Programmer :  Ben Deininger
 * 		
 *  Purpose :  This class creates an instance of a game board, which is a 2d array of Tile objects.  A tile object will 
 *  			tentatively contain simple ownership and a resource.  There will be a collection of players for the board.
 *  			The constructor requires the row and column size and the number of players. 
 * 
 */

public class GameBoard {

	private Tile[][] map;
	private int rows;
	private int cols;
	private static Random rand = new Random();
	private float averageHeight = 0f; // Stores the average height of the noise
										// map.

	private static final float foodMult = 0.050f; // 5% of tiles that can
													// support food have food
	private static final float woodMult = 0.20f; // 5% of tiles that can support
													// wood have wood
	private static final float stoneMult = 0.050f; // 5% of tiles that can
													// support stone have stone
	private static final float goldMult = 0.002f; // 0.2% of tiles that can
													// support gold have gold

	// TODO for tiles add a coordinate (upper left and right).

	/*
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
	 * Notes: To complete a gameboard object, you will need to call a resource
	 * distribution method and a player distribution method (TODO implement a
	 * player dist method).
	 */
	public GameBoard(int row, int col) {
		rows = row;
		cols = col;
		map = new Tile[row][col];

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

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				float height = noisemap[r][c];
				map[r][c] = new Tile(Resource.NONE, height + heightAdjust,
						(float) r, (float) c); // Use the adjusted height to
												// create the tile
			}
		}

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

	/*
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

		System.out.println("Distributing resources in 'Natural' Fashion...");
		System.out.println("Distributing food...");
		int numFood = (int) (foodMult * (terrainList.get(
				Terrain.WATER.ordinal()).size()
				+ terrainList.get(Terrain.DIRT.ordinal()).size() + terrainList
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

	/*
	 * resourceDistNaturalHelp(): Description: Distributes one given resource.
	 * 
	 * Parameters:
	 * 
	 * @param terrainList - list of available terrain tiles that can have
	 * resources
	 * 
	 * @param numRes - number of specific resource to distribute
	 * 
	 * @param res - resource to distribute
	 * 
	 * @param offset - offset in terrainList, first list this resource can
	 * appear in
	 * 
	 * @param numLists - number of lists this resource can appear in.
	 * 
	 * Return Value:
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

	/*
	 * resourceDistStoneMountain(): Description: Another resource distribution
	 * function, this one makes a majority of the Mountain terrain type stone
	 */
	public void resourceDistStoneMountain() {
		// TODO make all of mountain area stone, distrubite some of other
		// resources
	}

	/*
	 * resourceDistGoldRush(): Description: Another resource distribution
	 * function, this one makes a majority of the snow and mountain terrain
	 * gold.
	 */
	public void resourceDistGoldRush() {
		// TODO make all of snow/ a lot of mountain gold resources, have gold in
		// water too?
	}

	/*
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

	/*
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

	/*
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
	 * Return Value:
	 * 
	 * @return Tile object at location x,y
	 */
	public Tile getTileAt(int x, int y) {
		return map[x][y];
	}

	/*
	 * placeBuildingAt(): Description: Places a building object at the location
	 * (x,y) on the map.
	 * 
	 * Parameters:
	 * 
	 * @param Building b - building to place on map
	 * 
	 * @param int x - x coordinate where to place building (0 to rows - 1)
	 * 
	 * @param int y - y coordinate where to place building (0 to cols - 1)
	 */

	// TODO: check if occupied
	public void placeBuildingAt(Building b, int x, int y) {

		// the tile must be owned to place a building

		// calculate the distance to the the bottom right corner
		int dc = (cols - (x + b.getWidth() - 1));
		int dr = (rows - (y + b.getHeight() - 1));

		// if the placement of the building does not go off the map
		if (dc > 0 && dr > 0) {

			// TODO: update how buildings are placed on the gameboard.
			// map[x][y].getOwner().addBuilding(b);

			// set the area of the rectangle to be occupied by a building

			for (int r = x; r < (x + b.getHeight()); r++) {

				for (int c = y; c < (y + b.getWidth()); c++) {
					map[r][c].setBuilding(b);
				}
			}
		} else {
			System.err.println("The building does not fit on the map");
		}
	}

	/*
	 * removeUnit(): Description: Removes a Unit from a Players unit list? (TODO
	 * shouldn't this be with player?)
	 * 
	 * Parameters:
	 * 
	 * @param Unit u - unit to remove
	 */
	public void removeUnit(Unit u) {
		// remove from gameboard
	}

	/*
	 * moveUnit(): Description: Moves a unit from current location to new one.
	 * TODO implement some sort of restriction on distance TODO possibly move
	 * into unit class? NOTE: currently does not check to make sure that move is
	 * within bounds
	 * 
	 * Parameters:
	 * 
	 * @param Unit u - unit to move
	 * 
	 * @param int x - new x coordinate for unit
	 * 
	 * @param int y - new y coordinate for unit
	 */
	public void moveUnit(Unit u, int x, int y) {

		u.setLocation(x, y);

	}

	/*
	 * removeBuilding(): Description: removes a given building from the board
	 * and sets the tiles that were occupied by the building to vacant. Note:
	 * Building must be within range
	 * 
	 * Parameters:
	 * 
	 * @param Building b - building to remove from this gameboard.
	 */
	public void removeBuilding(Building b) {

		Point pos = b.getPosition();
		int x = Math.round(pos.x);
		int y = Math.round(pos.y);
		map[x][y].removeBuilding();

		int height = b.getHeight();
		int width = b.getWidth();

		for (int r = x; r < (x + height); r++) {

			for (int c = y; c < (y + width); c++) {
				map[r][c].setIsOccupiedByBuilding(false);
			}
		}
	}

	/*
	 * Gabe Kishi - DiamondSquare Procedurally generates terrain using a height
	 * map produced by the Diamond Squares algorithm
	 * http://en.wikipedia.org/wiki/Diamond-square_algorithm
	 */

	// Note: removed 'static' modifier
	public float[][] diamondSquareGenerator(int SIZE, long seed, float roughness) {
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
