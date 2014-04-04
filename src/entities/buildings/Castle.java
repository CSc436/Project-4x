package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;

public class Castle extends Building {

	// main hub

	private int populationCap;
	private int influenceArea;

	public Castle(UUID id, int playerId, float xco,
			float yco, int populationCap, int influenceArea) {
		super(id, playerId, BaseStatsEnum.CASTLE, BaseStatsEnum.CASTLE.getStats(), 
				BuildingType.CASTLE, xco,
				yco, 2, 2);
		this.populationCap = populationCap;
		this.influenceArea = influenceArea;
	}

	public int getPopulationCap()
	{
		return populationCap;
	}
	
	public int getInfluenceArea()
	{
		return influenceArea;
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
