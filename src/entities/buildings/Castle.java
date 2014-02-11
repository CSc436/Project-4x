package entities.buildings;

import entities.Player;

public class Castle extends Building {

	// main hub
	private int castleLevel;
	private int populationCap;
	private int influenceArea;

	public Castle(Player p, int h, int w) {
		super(p, h, w, 1000);
		castleLevel = 1;
		populationCap = 100; // Random number for now.
		influenceArea = 2; // a nxn radius from the row and column of its board
							// tile.
	}
}
