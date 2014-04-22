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
	protected Resources baseResourceAmount;
	protected Resources resourceAmount;


	public StoneMine(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.STONE_MINE, BaseStatsEnum.STONE_MINE
				.getStats(), BuildingType.STONE_MINE, xco, yco, 1, 1);
		baseResourceAmount = new Resources(0, 0, 0, 1000, 0);
		resourceAmount = new Resources(0, 0, 0, 10, 0);
	}

	public Resources generateResource() {

		return resourceAmount;
	}

	@Override
	public void advanceResourceProduction() {

		if (baseResourceAmount.spend(0, 0, 0, 10, 0)) {

			this.getPlayer().addResources(resourceAmount);
		}

		return;
	}

	public Resources getBaseResources() {

		return baseResourceAmount;
	}

}
