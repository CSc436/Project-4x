package entities.buildings;

/**
 * BuildingType
 * 
 * @author NRTop Specifies the type of building, or resource building
 */
public enum BuildingType {
	BARRACKS(2, 2), CASTLE(4, 4), BANK(2, 2), TOWN_HALL(4, 4),

	// Resource Buildings
	LUMBER_MILL(1, 1), STONE_MINE(1, 1), GOLD_MINE(1, 1), FARM(1, 1), UNIVERSITY(
			3, 3);

	int x;
	int y;

	private BuildingType(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
