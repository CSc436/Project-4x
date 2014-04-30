package com.shared.model.buildings;

import java.util.HashMap;
import java.util.UUID;

import com.shared.model.control.BuildingType;
import com.shared.model.resources.Resources;
import com.shared.model.stats.BaseStatsEnum;

public class Town_Hall extends ResourceBuilding {

	boolean iscapital;

	/*
	 * trading : 1 trade cart per town hall, each trade cart will autonomously
	 * go from the capital to its designated trading city. This will be the same
	 * for allies, perhaps a bonus for trading with the allies capital city
	 */

	public Town_Hall(int id, int playerId, float xco, float yco, boolean iscapital) {
		super(id, playerId, BaseStatsEnum.TOWN_HALL, BaseStatsEnum.TOWN_HALL.getStats(), 
				BuildingType.TOWN_HALL,
				xco, yco, 4, 4, new Resources(0, 0, 0, 0, 0));

		this.iscapital = iscapital;

	}
	
	public Town_Hall() {}

}
