package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

/**
 * University 
 * @author NRTop
 * building to generate research points
 */
public class University extends ResourceBuilding{

	public University(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.UNIVERSITY, BaseStatsEnum.UNIVERSITY.getStats(), 
				BuildingType.UNIVERSITY, xco,
				yco, 2, 2, new Resources(0, 0, 0, 0, 10));
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
