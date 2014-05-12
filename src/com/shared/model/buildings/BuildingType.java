package com.shared.model.buildings;

import java.io.Serializable;

import com.shared.model.gameboard.Resource;

/**
 * BuildingType
 * 
 * @author NRTop Specifies the type of building, or resource building
 */
public enum BuildingType implements Serializable {
	
	// Production
	
	BARRACKS(2, 2), CASTLE(4, 4), BANK(2, 2), TOWN_HALL(4, 4),

	// Resource Buildings
	LUMBER_MILL(1, 1), STONE_MINE(1, 1), GOLD_MINE(1, 1), FARM(1, 1), UNIVERSITY(
			3, 3),

	// Research Buildings

	BLACKSMITH(2, 2);

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
	
	public String toStringDisplay() {
		return this.toString().replace('_', ' ');
	}
	
	public static BuildingType fromDisplayToEnum(String str) {
		return BuildingType.valueOf(str.replace(' ', '_'));
	}
	
	public boolean canBuild(Resource r) {
		if (r.equals(Resource.NONE)) {
			// Can only build resource buildings (except university) on resources
			if (this.equals(LUMBER_MILL) || this.equals(STONE_MINE) || this.equals(FARM) || this.equals(GOLD_MINE)) {
				return false;
			} else {
				return true;
			}
		} else if (r.equals(Resource.FOOD) && !this.equals(FARM)) {
			// Can only build farms on food resources
			return false;
		} else if (r.equals(Resource.GOLD) && !this.equals(GOLD_MINE)) {
			// Can only build gold mines on gold resources
			return false;
		} else if (r.equals(Resource.STONE) && !this.equals(STONE_MINE)) {
			// Can only build stone mines on stone resources
			return false;
		} else if (r.equals(Resource.WOOD) && !this.equals(LUMBER_MILL)) {
			// Can only build lumber mills on wood resources
			return false;
		} else {
			return true;
		}
	}
}
