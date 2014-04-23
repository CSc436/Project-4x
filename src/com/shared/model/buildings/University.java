package com.shared.model.buildings;

import java.util.HashMap;
import java.util.UUID;

import com.shared.model.control.GameModel;
import com.shared.model.resources.Resources;
import com.shared.model.stats.BaseStatsEnum;

/**
 * University
 * 
 * @author NRTop building to generate research points
 */
public class University extends Building implements ResourceBuilding {

	protected Resources baseResourceAmount;
	protected Resources resourceAmount;

	/**
	 * University() creates a University
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

		/* University will start with 5000 research points and deal 20 each tick */
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
