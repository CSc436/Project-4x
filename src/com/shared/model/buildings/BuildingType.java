package com.shared.model.buildings;

import java.io.Serializable;

import com.shared.model.gameboard.Resource;
import com.shared.model.resources.Resources;

/**
 * BuildingType
 * 
 * @author NRTop Specifies the type of building, or resource building
 */
public enum BuildingType implements Serializable {

	// Production

	BARRACKS(2, 2, new Resources(200, 200, 100, 100, 0)), 
	CASTLE(4, 4, new Resources(0, 0, 0, 0, 0)), 
	BANK(2, 2, new Resources(0, 0, 0, 0, 0)),
	TOWN_HALL(4, 4, new Resources(0, 0, 0, 0, 0)),

	// Resource Buildings
	LUMBER_MILL(1, 1, new Resources(100, 200, 0, 50, 0)), 
	STONE_MINE(1, 1, new Resources(100, 50, 0, 200, 0)),
	GOLD_MINE(1, 1, new Resources(200, 50, 0, 100, 0)),
	FARM(1, 1, new Resources(100, 50, 50, 50, 0)), 
	UNIVERSITY(3, 3, new Resources(0, 0, 0, 0, 0)),

	// Research Buildings

	BLACKSMITH(2, 2, new Resources(0, 0, 0, 0, 0));

	int x;
	int y;
	Resources resourcesCost;

	private BuildingType(int x, int y, Resources resourcesCost) {
		this.x = x;
		this.y = y;
		this.resourcesCost = resourcesCost;
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

	public String toStringWithResources() {
		String string = "<span>" + this.toString().replace('_', ' ')
				+ "</span><br>";
		string += "Gold: " + this.resourcesCost.getGold() + "<br>";
		string += "Food: " + this.resourcesCost.getFood() + "<br>";
		string += "Stone: " + this.resourcesCost.getStone() + "<br>";
		string += "Wood: " + this.resourcesCost.getWood() + "<br>";
		string += "Research: " + this.resourcesCost.getResearchPts() + "<br>";
		return string;
	}

	public boolean canBuild(Resource r) {
		if (r.equals(Resource.NONE)) {
			// Can only build resource buildings (except university) on
			// resources
			if (this.equals(LUMBER_MILL) || this.equals(STONE_MINE)
					|| this.equals(FARM) || this.equals(GOLD_MINE)) {
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

	public Resources getResourcesCost() {
		return resourcesCost;
	}
}
