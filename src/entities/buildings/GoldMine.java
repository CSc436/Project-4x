package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.GameModel;
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

	public GoldMine(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.GOLD_MINE, BaseStatsEnum.GOLD_MINE
				.getStats(), BuildingType.GOLD_MINE, xco, yco, 1, 1);
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
