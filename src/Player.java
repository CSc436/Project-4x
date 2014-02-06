public class Player {

	private String name;
	// GET / SET
	public int getHealth() {
		return health;
	}

	public Player getOwner() {
		return owner;
	}
	public Player(String alias) {
		name = alias;

	}

	public void setName(String newName) {

		name = newName;
	}

}
