package entities.buildings;

import java.util.UUID;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

/**
 * Farm
 * 
 * @author NRTop The Farm allows Users to generate Food resources.
 */
public class Farm extends Building implements ResourceBuilding {

	protected final Resources baseResourceAmount = new Resources(0, 100, 0, 0,
			0);
	protected Resources resourceAmount;

	// Global resource rate modifier that affects all resource buildings

	/**
	 * Farm() creates a new farm, must be placed on a food tile.
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
	public Farm(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.FARM, BaseStatsEnum.FARM.getStats(),
				BuildingType.FARM, xco, yco, 1, 1);

	}

	/* Setters and Getters */
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
