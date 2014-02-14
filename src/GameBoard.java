import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import entities.Player;
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
		Random rand = new Random();
		rand.setSeed(16);

		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {

				// give the tile a random resource number
				int resource = rand.nextInt(16);
				float height = noisemap[r][c];

				// create a new tile

				map[r][c] = new Tile(resource, height);
				// System.out.print(map[r][c].getHeight() + " ");
				// System.out.printf("%10s",map[r][c].getTerrainType());
			}
			// System.out.println();
		}
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

				for (int r = x; r < b.getWidth(); r++) {

					for (int c = y; c < b.getHeight(); c++) {
						map[r][c].setIsOccupiedByBuilding(true);
					}
				}
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

	// precondition : the building is guaranteed to be in range
	public void removeBuilding(Building b) {

		Player owner = b.getOwner();
		int x = b.getX();
		int y = b.getY();

		int height = b.getHeight();
		int width = b.getWidth();

		for (int r = 0; r < b.getWidth(); r++) {

			for (int c = 0; c < b.getHeight(); c++) {
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

	public static float[][] diamondSquareGenerator(int SIZE, long seed,
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

		return noise;
	}

	private static void dsIter(float[][] noise, int size, float roughness) {
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
