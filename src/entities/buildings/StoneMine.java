package entities.buildings;

import java.util.UUID;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

/**
 * StoneMine
 * 
 * @author NRTop This resourceBuilding allows user to collect stone resources
 */
public class StoneMine extends Building implements ResourceBuilding {

	/**
	 * StoneMine() Constructor for a StoneMine building, creates a resource
	 * building which generates 20 stone.
	 * 
	 * @param id
	 *            - UUID of this building
	 * @param playerId
	 *            - id of the player who own's this building
	 * @param xco
	 *            - x coordinate of the building
	 * @param yco
	 *            - y coordinate of the building
	 */
	public StoneMine(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.STONE_MINE, BaseStatsEnum.STONE_MINE
				.getStats(), BuildingType.STONE_MINE, xco, yco, 1, 1);
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

	@Override
	public void setGlobalRateModifier(Resources newRate) {
		// TODO Auto-generated method stub

	}

}
