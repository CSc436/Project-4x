package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.GameModel;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

/**
 * Farm
 * @author NRTop
 * The Farm allows Users to generate Food resources. 
 */
public class Farm extends ResourceBuilding{

	/**
	 * Farm()
	 * creates a new farm, must be placed on a food tile.
	 * @param id - UUID of this building
	 * @param playerId - id of the player who own's this building
	 * @param xco - x coordinate of the building
	 * @param yco - y coordinate of the building
	 */
	public Farm(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.FARM, BaseStatsEnum.FARM.getStats(), 
				BuildingType.FARM, xco,
				yco, 1, 1, new Resources(0, 0, 20, 0, 0));
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
