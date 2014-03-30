package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;

public class StoneMine extends ResourceBuilding{

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

}
