package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class Farm extends ResourceBuilding{

	/**
	 * Farm()
	 * creates a new farm, must be placed on a food tile.
	 * @param id
	 * @param playerId
	 * @param baseStats
	 * @param new_stats
	 * @param gameObjectType
	 * @param buildingType
	 * @param xco
	 * @param yco
	 * @param height
	 * @param width
	 */
	public Farm(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats,
			BuildingType buildingType, float xco, float yco, int height,
			int width) {
		super(id, playerId, baseStats, new_stats, buildingType, xco,
				yco, height, width, new Resources(0, 0, 20, 0, 0));
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
