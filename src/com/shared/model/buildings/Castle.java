package com.shared.model.buildings;

import java.util.Queue;

import com.shared.model.behaviors.Producer;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.UnitType;

public class Castle extends Building implements Producer {

	// main hub

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406038330060341620L;
	private int populationCap;
	private int influenceArea;
	private Producer productionBehavior;

	public Castle(int id, int playerId, float xco,
			float yco, int populationCap, int influenceArea, Producer p) {
		super(id, playerId, BaseStatsEnum.CASTLE, BaseStatsEnum.CASTLE.getStats(), 
				BuildingType.CASTLE, xco,
				yco, 2, 2);
		this.populationCap = populationCap;
		this.influenceArea = influenceArea;
		this.productionBehavior = p;
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

	@Override
	public void simulateProduction(int timeStep) {
		productionBehavior.simulateProduction(timeStep);
	}

	@Override
	public void queueProduction(UnitType type) {
		productionBehavior.queueProduction(type);
	}

	@Override
	public Queue<UnitType> getProducedUnits() {
		return productionBehavior.getProducedUnits();
	}
	
}
