package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.pawns.Trade_Cart;

public class Town_Hall extends Building implements ProductionBuilding {

	Trade_Cart trader;
	boolean iscapital;
	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	/*
	 * trading : 1 trade cart per town hall, each trade cart will autonomously
	 * go from the capital to its designated trading city. This will be the same
	 * for allies, perhaps a bonus for trading with the allies capital city
	 */

	public Town_Hall(UUID id, int playerId, float xco, float yco,
			boolean iscapital) {
		super(id, playerId, BaseStatsEnum.TOWN_HALL, BaseStatsEnum.TOWN_HALL
				.getStats(), BuildingType.TOWN_HALL, xco, yco, 4, 4);

		this.iscapital = iscapital;

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

}
