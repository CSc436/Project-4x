package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class Castle extends Building {

	// main hub

	private int populationCap;
	private int influenceArea;

	public Castle(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, 
			BuildingType buildingType, float xco,
			float yco, int height, int width, int populationCap, int influenceArea) {
		super(id, playerId, baseStats, new_stats, 
				buildingType, xco,
				yco, height, width);
		this.populationCap = populationCap;
		this.influenceArea = influenceArea;
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
