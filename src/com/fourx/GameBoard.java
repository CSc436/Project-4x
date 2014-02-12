package com.fourx;
import java.util.ArrayList;

/* 
 *  Programmer :  Ben Deininger
 * 		
 *  Purpose :  This class creates an instance of a game board, which is a 2d array of Tile objects.  A tile object will 
 *  			tentatively contain simple ownership and a resource.  There will be a collection of players for the board.
 *  			The constructor requires the row and column size and the number of players. 
 * 
 */

public class GameBoard {

	Tile[][] map;
	private int rows;
	private int cols;
	ArrayList<Player> players;

	public GameBoard(int row, int col, int numPlayers) {
		rows = row;
		cols = col;
		map = new Tile[row][col];

		// needs optimization!
		final long startTime = System.currentTimeMillis();

		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {

				map[r][c] = new Tile();

			}
		}
		final long endTime = System.currentTimeMillis();
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

}
