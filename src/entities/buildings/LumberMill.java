package entities.buildings;

import java.util.UUID;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

/**
 * LumberMill
 * 
 * @author NR Top The lumberMill allows the user to generate wood resources
 */
public class LumberMill extends Building implements ResourceBuilding {

	/**
	 * LumberMill() creates a new lumber mill object
	 * 
	 * @param id
	 *            - unique id for this lumber mill
	 * @param playerId
	 *            - id of player who owns this lumber mill
	 * @param xco
	 *            - x coordinate of this mill
	 * @param yco
	 *            - y coordinate of this mill
	 */
	public LumberMill(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.LUMBER_MILL,
				BaseStatsEnum.LUMBER_MILL.getStats(), BuildingType.LUMBER_MILL,
				xco, yco, 1, 1);
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
