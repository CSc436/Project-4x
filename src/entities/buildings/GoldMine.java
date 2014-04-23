package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.GameModel;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

/**
 * GoldMine()
 * @author NRTop
 * The goldMine allows players to generate gold resources. 
 */
public class GoldMine extends ResourceBuilding{

	/**
	 * GoldMine():
	 * @param id - unique id for this GoldMine
	 * @param playerId - id of player who owns this goldmine
	 * @param xco - xcoordinate of this mine
	 * @param yco = y coordinate of this mine
	 */
	public GoldMine(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.GOLD_MINE, BaseStatsEnum.GOLD_MINE.getStats(), 
				BuildingType.GOLD_MINE, xco,
				yco, 1, 1, new Resources(20, 0, 0, 0, 0));
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

	@Override
	public void tick(int timeScale, GameModel model) {
		// TODO Auto-generated method stub
		
	}

}
