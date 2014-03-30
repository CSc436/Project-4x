package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.GameObjectType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

/**
 * University 
 * @author NRTop
 * building to generate research points
 */
public class University extends ResourceBuilding{

	public University(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats,
			BuildingType buildingType, float xco, float yco, int height,
			int width) {
		super(id, playerId, baseStats, new_stats, buildingType, xco,
				yco, height, width, new Resources(0, 0, 0, 0, 10));
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
