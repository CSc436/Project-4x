package controller;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import entities.Player;
import entities.Resource;
import entities.Terrain;
import entities.Tile;
import entities.buildings.Building;
import entities.units.Infantry;
import entities.units.Unit;

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
	private ArrayList<Player> players;
	private static Random rand = new Random();
	private float averageHeight = 0f; // Stores the average height of the noise
										// map.

	private static float foodMult  = 0.050f;  	// 5% of tiles that can support food have food
	private static float woodMult  = 0.050f; 	// 5% of tiles that can support wood have wood
	private static float stoneMult = 0.050f; 	// 5% of tiles that can support stone have stone
	private static float goldMult  = 0.002f; 	// 0.2% of tiles that can support gold have gold
	
	public GameBoard(int row, int col, int numPlayers) {
		rows = row;
		cols = col;
		map = new Tile[row][col];

		// create players with default "playerIDno" names

		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player("player" + i));
		}

		// create a noisemap
		System.out.println("Building noisemap...");
		long startTime = System.currentTimeMillis();
		float[][] noisemap = diamondSquareGenerator(row,
				System.currentTimeMillis(), 0.1f);
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime));

		// create tiles
		// needs optimization!
		System.out.println("Building Board...");
		startTime = System.currentTimeMillis();

		// create a random number generator
		// Random rand = new Random();
		// rand.setSeed(16);

		System.out.println("\nAverage Height: " + this.averageHeight);

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
		System.out.println("Height Adjust: " + heightAdjust + "\n");

		// ---------------------------------------------------------------------------------------------
		// Default all to NONE. Then create distributions of all other
		// resources.
		ArrayList<ArrayList<Point>> terrainList = new ArrayList<ArrayList<Point>>();
		for (int i = 0; i < 6; i++)
		{
			terrainList.add(new ArrayList<Point>());
		}
		
		
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {

				// give the tile a random resource number
				//int resource = rand.nextInt(16);
				float height = noisemap[r][c];

				map[r][c] = new Tile(Resource.NONE, height + heightAdjust); // Use the adjusted height to create the tile
				terrainList.get(map[r][c].getTerrainType().ordinal()).add(new Point(r, c));
			}
			// System.out.println();
		}
		

		// TODO issue with this strategy, if no terrain of certain type, will never finish distributing 
		// Resources EDIT: added quick fail safe. Still not best approach. 
		// TODO to add clustering (More realistic). 
		
		// TODO instead of generating percentage of resource based on total map size, determine based 
		// on size of 
		Random rnd = new Random();
		int r, rp,rpp;
		Point temp;
	
		System.out.println("Base board build, distributing resources...");
		System.out.println("Distributing food...");
		int numFood = (int) (foodMult * (terrainList.get(Terrain.WATER.ordinal()).size() +
				                         terrainList.get(Terrain.DIRT.ordinal()).size() +
				                         terrainList.get(Terrain.GRASS.ordinal()).size()));
		// food can exist in lists 0,1,2
		while (numFood > 0)
		{
			r   = Math.abs(rnd.nextInt());
			rp  = r % 3;
			rpp = r % terrainList.get(rp).size();
			if (terrainList.get(rp).size() == 0)
			{
				continue;
			}
			do
			{
				if (terrainList.get(r % 3).size() == 0)
				{
					break;
				}
				temp = terrainList.get(rp).get(rpp % terrainList.get(rp).size());
				map[temp.x][temp.y].setResource(Resource.FOOD);
				terrainList.get(rp).remove(rpp % terrainList.get(rp).size());
				numFood--;
			} while(rnd.nextBoolean());
		}
		
		System.out.println("Distributing wood...");
		int numWood = (int) (woodMult * (terrainList.get(Terrain.GRASS.ordinal()).size() +
                                         terrainList.get(Terrain.HILL.ordinal()).size()));
		// Wood can exist in lists 2 and 3
		while (numWood > 0)
		{
			r   = Math.abs(rnd.nextInt());
			rp  = 2 + r % 2;
			if (terrainList.get(rp).size() == 0)
			{
				continue;
			}
			rpp = r % terrainList.get(rp).size();
			do 
			{
				if ( terrainList.get(rp).size() == 0)
				{
					break;
				}
				temp = terrainList.get(rp).get(rpp % terrainList.get(rp).size());
				map[temp.x][temp.y].setResource(Resource.WOOD);
				terrainList.get(rp).remove(rpp % terrainList.get(rp).size());
				numWood--;
			} while(rnd.nextBoolean());
		}
		
		System.out.println("Distributing stone...");
		int numStone = (int) (stoneMult * (terrainList.get(Terrain.HILL.ordinal()).size() +
                                           terrainList.get(Terrain.MOUNTAIN.ordinal()).size() + 
                                           terrainList.get(Terrain.SNOW.ordinal()).size()));
		// stone can exist in lists 3, 4, 5
		while (numStone > 0)
		{
			r   = Math.abs(rnd.nextInt());
			rp  = 3 + r % 3;
			if (terrainList.get(rp).size() == 0)
			{
				continue;
			}
			rpp = r % terrainList.get(rp).size();
			do 
			{
				if (terrainList.get(rp).size() == 0)
				{
					break;
				}
				temp = terrainList.get(rp).get(rpp % terrainList.get(rp).size());
				map[temp.x][temp.y].setResource(Resource.STONE);
				terrainList.get(rp).remove(rpp % terrainList.get(rp).size());
				numStone--;
			} while(rnd.nextBoolean());
		}
		
		System.out.println("Distributing gold...");
		int numGold = (int) (goldMult * (terrainList.get(Terrain.HILL.ordinal()).size() +
                                         terrainList.get(Terrain.MOUNTAIN.ordinal()).size() + 
                                         terrainList.get(Terrain.SNOW.ordinal()).size()));
		while (numGold > 0)
		{
			r   = Math.abs(rnd.nextInt());
			rp  = 3 + r % 3;
			if (terrainList.get(rp).size() == 0)
			{
				continue;
			}
			rpp = r % terrainList.get(rp).size();
			do
			{
				if (terrainList.get(rp).size() == 0)
				{
					break;
				}
				temp = terrainList.get(rp).get(rpp % terrainList.get(rp).size());
				map[temp.x][temp.y].setResource(Resource.GOLD);
				terrainList.get(rp).remove(rpp % terrainList.get(rp).size());
				numGold--;
			} while(rnd.nextBoolean());
		}

		// TODO distribute players
		// Based on num players
		// 1 - place player roughly in center
		// 2 - place players caddy corner
		// 3 + 4 - place players in corners of map
		// 5 - 1-4 place in corner, 5 place in center.
		// attempt to distribute near resources.

		endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime));

	}

	// -----------------------------------------------------------------------------------------
	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;

	}

	public Tile getTileAt(int x, int y) {
		return map[x][y];
	}

	public ArrayList<Player> getPlayerList() {
		return players;
	}

	public void placeUnitAt(Unit u, int x, int y) {

		// if the tile has no owner, the unit owner becomes the tile owner

		if (!map[x][y].hasOwner()) {
			map[x][y].setOwner(u.getOwner());
		}

		// set the unit's coodinates
		u.setLocation(x, y);

	}

	public void placeBuildingAt(Building b, int x, int y) {

		// the tile must be owned to place a building
		if (b.getOwner().equals(map[x][y].getOwner())) {

			// calculate the distance to the the bottom right corner
			int dc = (cols - (x + b.getWidth() - 1));
			int dr = (rows - (y + b.getHeight() - 1));

			// if the placement of the building does not go off the map
			if (dc > 0 && dr > 0) {

				map[x][y].getOwner().addBuilding(b);

				// set the area of the rectangle to be occupied by a building

				for (int r = x; r < (x + b.getHeight()); r++) {

					for (int c = y; c < (y + b.getWidth()); c++) {
						map[r][c].setIsOccupiedByBuilding(true);
					}
				}

				b.setLocation(x, y);
			} else {
				System.err.println("The building does not fit on the map");
			}
		} else {
			System.err
					.println("The owner of the building does not own the tile");
		}
	}

	public void removeUnit(Unit u) {

		Player owner = u.getOwner();
		owner.removeUnit(u);

	}

	// need restriction for distance

	public void moveUnit(Unit u, int x, int y) {

		u.setLocation(x, y);

	}

	// precondition : the building is guaranteed to be in range
	public void removeBuilding(Building b) {

		Player owner = b.getOwner();
		int x = b.getX();
		int y = b.getY();

		int height = b.getHeight();
		int width = b.getWidth();

		for (int r = x; r < (x + b.getHeight()); r++) {

			for (int c = y; c < (y + b.getWidth()); c++) {
				map[r][c].setIsOccupiedByBuilding(false);
			}
		}

		owner.removeBuilding(b);

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
