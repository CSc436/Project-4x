package entities;

public abstract class Building {

	// buildings will be > 1 tile

	
	
	// initial building types

	// unit producing
	// resource producing
	// research options
	// defense units

	private int health;
	private Player owner; // owner of the structure
	private int height; // height of the structure
	private int width; // width of the structure
	private int x = -1;
	private int y = -1; // dimensions on the game board

	public Building(Player p, int h, int w) {

		health = 100;
		owner = p;
		height = h;
		width = w;

	}

	// GET / SET
	public int getHealth() {
		return health;
	}

	public Player getOwner() {
		return owner;
	}

	public void setLocation(int row, int col) {

		x = row;
		y = col;
	}

}
