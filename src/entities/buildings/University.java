package entities.buildings;

import java.util.UUID;

import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

/**
 * University
 * 
 * @author NRTop building to generate research points
 */
public class University extends Building implements ResourceBuilding {

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
	public University(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.STONE_MINE, BaseStatsEnum.STONE_MINE
				.getStats(), BuildingType.STONE_MINE, xco, yco, 1, 1);
		baseResourceAmount = new Resources(0, 0, 0, 0, 5000);
		resourceAmount = new Resources(0, 0, 0, 0, 20);
	}

	public Resources generateResource() {

		return resourceAmount;
	}

	@Override
	public void advanceResourceProduction() {

		if (baseResourceAmount.spend(0, 0, 0, 0, 20)) {

			this.getPlayer().addResources(resourceAmount);
		}

		return;
	}

	public Resources getBaseResources() {

		return baseResourceAmount;
	}
}
