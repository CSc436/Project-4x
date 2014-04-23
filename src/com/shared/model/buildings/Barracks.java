package com.shared.model.buildings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import com.shared.model.control.GameModel;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;

public class Barracks extends Building implements ProductionBuilding {

	int level;
	int soldierNum;
	int newSoldNum;
	int capacity;
	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	public Barracks(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.BARRACKS, BaseStatsEnum.BARRACKS
				.getStats(), BuildingType.BARRACKS, xco, yco,
				BuildingType.BARRACKS.getX(), BuildingType.BARRACKS.getY());
	}

	// ???????
	public int createSoldiers() {
		switch (level) {
		case (1):
			return 50;
		case (2):
			return 80;
		case (3):
			return 100;
		case (4):
			return 150;
		case (5):
			return 200;
		default:
			return 0;
		}
	}

	public int getSoldiers() {
		soldierNum += createSoldiers();
		return soldierNum;
	}

	public void upgrade() {
		level++;
	}

	public void setCapacity() {
		switch (level) {
		case 1:
			capacity = 1;
		case 2:
			capacity = 5;
		case 3:
			capacity = 10;
		case 4:
			capacity = 17;
		case 5:
			capacity = 25;
		}
	}

	public Unit advanceUnitProduction(int timestep) {

		if (!productionQueueEmpty()) {
			Unit u = buildingQ.peek();
			u.decrementCreationTime(timestep);
			if (u.getCreationTime() <= 0) {

				return buildingQ.poll();
			}
		}
		return null;
	}
	
	public boolean queueUnit(Unit u) {
		return buildingQ.add(u);

	}

	public Unit dequeueUnit() {
		return buildingQ.poll();
	}

	public boolean productionQueueEmpty() {

		return buildingQ.isEmpty();
	}

	public Unit getProducingUnit() {
		return buildingQ.peek();
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
