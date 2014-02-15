package entities.buildings;

import java.util.HashMap;

import control.Player;


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

	@Override
	protected void setActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}
}
