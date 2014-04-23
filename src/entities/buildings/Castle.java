package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.GameModel;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class Castle extends Building implements ProductionBuilding {

	// main hub

	private int populationCap;
	private int influenceArea;

	public Castle(UUID id, int playerId, float xco, float yco,
			int populationCap, int influenceArea) {
		super(id, playerId, BaseStatsEnum.CASTLE, BaseStatsEnum.CASTLE
				.getStats(), BuildingType.CASTLE, xco, yco, 2, 2);
		this.populationCap = populationCap;
		this.influenceArea = influenceArea;
	}

	public int getPopulationCap() {
		return populationCap;
	}

	public int getInfluenceArea() {
		return influenceArea;
	}

	@Override
	public Unit advanceUnitProduction(int timestep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean queueUnit(Unit u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit dequeueUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean productionQueueEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit getProducingUnit() {
		// TODO Auto-generated method stub
		return null;
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
