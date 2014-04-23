package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.GameModel;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

/**
 * StoneMine
 * @author NRTop
 * This resourceBuilding allows user to collect stone resources
 */
public class StoneMine extends ResourceBuilding{

	/**
	 * StoneMine()
	 * Constructor for a StoneMine building, creates a resource building which 
	 * generates 20 stone. 
	 * @param id - UUID of this building
	 * @param playerId - id of the player who own's this building
	 * @param xco - x coordinate of the building
	 * @param yco - y coordinate of the building
	 */
	public StoneMine(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.STONE_MINE, BaseStatsEnum.STONE_MINE.getStats(), 
				BuildingType.STONE_MINE, xco,
				yco, 1, 1, new Resources(0, 0, 0, 20, 0));
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
