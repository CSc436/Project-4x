package entities;
public abstract class Building {

	private int health;
	private Player owner;

	public Building(Player p) {

		health = 100;
		owner = p;

	}

	// GET / SET
	public int getHealth() {
		return health;
	}

	public Player getOwner() {
		return owner;
	}
}
