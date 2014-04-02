package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

public class LumberMill extends ResourceBuilding{

	/**
	 * LumberMill()
	 * creates a new lumber mill object
	 * @param id - unique id for this lumber mill
	 * @param playerId - id of player who owns this lumber mill
	 * @param xco - x coordinate of this mill
	 * @param yco - y coordinate of this mill 
	 */
	public LumberMill(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.LUMBER_MILL, BaseStatsEnum.LUMBER_MILL.getStats(), 
				BuildingType.LUMBER_MILL, xco,
				yco, 1, 1, new Resources(0, 20, 0, 0, 0));
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
