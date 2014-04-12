package entities.buildings;

import java.util.UUID;

import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

/**
 * University
 * 
 * @author NRTop building to generate research points
 */
public class University extends Building implements ResourceBuilding {

	public University(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.UNIVERSITY, BaseStatsEnum.UNIVERSITY
				.getStats(), BuildingType.UNIVERSITY, xco, yco, 2, 2);
	}

	@Override
	public void setGlobalRateModifier(Resources newRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public Resources getGlobalRateModifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resources generateResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit advanceResourceProduction(int timestep) {
		// TODO Auto-generated method stub
		return null;
	}
}
