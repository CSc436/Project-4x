package entities.buildings;

import java.util.UUID;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

/**
 * GoldMine()
 * 
 * @author NRTop The goldMine allows players to generate gold resources.
 */
public class GoldMine extends Building implements ResourceBuilding {

	protected final Resources baseResourceAmount = new Resources(10000, 0, 0,
			0, 0);
	protected Resources resourceAmount;

	/**
	 * GoldMine():
	 * 
	 * @param id
	 *            - unique id for this GoldMine
	 * @param playerId
	 *            - id of player who owns this goldmine
	 * @param xco
	 *            - xcoordinate of this mine
	 * @param yco
	 *            = y coordinate of this mine
	 */
	public GoldMine(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.GOLD_MINE, BaseStatsEnum.GOLD_MINE
				.getStats(), BuildingType.GOLD_MINE, xco, yco, 1, 1);

	}

	public void setGlobalRateModifier(Resources newRate) {
		globalRateModifier.scale(newRate);
		resourceAmount = baseResourceAmount.scale(globalRateModifier);
	}

	public Resources getGlobalRateModifier() {
		return globalRateModifier;
	}

	public final Resources generateResource() {
		return resourceAmount;
	}

	@Override
	public Unit advanceResourceProduction(int timestep) {
		// TODO Auto-generated method stub
		return null;
	}

}
