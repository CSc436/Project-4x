package com.shared.model.buildings;

import com.shared.model.stats.BaseStatsEnum;

public class Castle extends Building {

	// main hub

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406038330060341620L;
	private int populationCap;
	private int influenceArea;

	public Castle(int id, int playerId, float xco,
			float yco, int populationCap, int influenceArea) {
		super(id, playerId, BaseStatsEnum.CASTLE, BaseStatsEnum.CASTLE.getStats(), 
				BuildingType.CASTLE, xco,
				yco, 2, 2);
		this.populationCap = populationCap;
		this.influenceArea = influenceArea;
	}
	
	public Castle() {}

	public int getPopulationCap()
	{
		return populationCap;
	}
	
	public int getInfluenceArea()
	{
		return influenceArea;
	}
	
}
