package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

public class Farm extends ResourceBuilding{

	/**
	 * Farm()
	 * creates a new farm, must be placed on a food tile.
	 * @param id
	 * @param playerId
	 * @param xco
	 * @param yco
	 */
	public Farm(int id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.FARM, BaseStatsEnum.FARM.getStats(), 
				BuildingType.FARM, xco,
				yco, 1, 1, new Resources(0, 0, 20, 0, 0));
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
