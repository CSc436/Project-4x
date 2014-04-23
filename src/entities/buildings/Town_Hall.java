package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.pawns.Trade_Cart;

public class Town_Hall extends Building implements ProductionBuilding,
		ResourceBuilding {

	Trade_Cart trader;
	boolean iscapital;
	protected Resources baseResourceAmount;
	protected Resources resourceAmount;
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
		baseResourceAmount = new Resources(0, 0, 0, 0, 1000);
		resourceAmount = new Resources(0, 0, 0, 0, 1);
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

	public Resources generateResource() {

		return resourceAmount;
	}

	@Override
	public void advanceResourceProduction() {

		if (baseResourceAmount.spend(0, 0, 0, 0, 1)) {

			this.getPlayer().addResources(resourceAmount);
		}

		return;
	}

	public Resources getBaseResources() {

		return baseResourceAmount;
	}

}
