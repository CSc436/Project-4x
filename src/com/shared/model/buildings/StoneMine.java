package com.shared.model.buildings;

import java.util.HashMap;
import java.util.UUID;

import com.shared.model.control.GameModel;
import com.shared.model.resources.Resources;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;

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

	@Override
	public void tick(int timeScale, GameModel model) {
		// TODO Auto-generated method stub
		
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
