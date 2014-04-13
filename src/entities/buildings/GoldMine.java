package entities.buildings;

import java.util.UUID;

import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

/**
 * GoldMine()
 * 
 * @author NRTop The goldMine allows players to generate gold resources.
 */
public class GoldMine extends Building implements ResourceBuilding {

	protected Resources baseResourceAmount;
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
	public GoldMine(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.FARM, BaseStatsEnum.FARM.getStats(),
				BuildingType.FARM, xco, yco, 1, 1);
		baseResourceAmount = new Resources(10000, 0, 0, 0, 0);
		resourceAmount = new Resources(20, 0, 0, 0, 0);
	}

	public Resources generateResource() {

		return resourceAmount;
	}

	@Override
	public void advanceResourceProduction() {

		if (baseResourceAmount.spend(20, 0, 0, 0, 0)) {

			this.getPlayer().addResources(resourceAmount);
		}

		return;
	}

	public Resources getBaseResources() {

		return baseResourceAmount;
	}

}
