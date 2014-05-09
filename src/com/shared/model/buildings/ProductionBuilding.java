package com.shared.model.buildings;


import java.util.Queue;

import com.shared.model.behaviors.Producer;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.stats.UnitStats;
import com.shared.model.units.UnitType;

public class ProductionBuilding extends Building implements Producer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6350344177302593568L;
	Producer productionBehavior;

	public ProductionBuilding(int id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, BuildingType buildingType, float xco,
			float yco, int height, int width, Producer p) {
		super(id, playerId, baseStats, new_stats, buildingType, xco, yco, height, width);
		productionBehavior = p;
	}
	
	public ProductionBuilding() {}

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
