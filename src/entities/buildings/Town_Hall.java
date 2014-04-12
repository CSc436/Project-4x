package entities.buildings;

import java.util.UUID;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.pawns.Trade_Cart;

public class Town_Hall extends Building implements ProductionBuilding {

	Trade_Cart trader;
	boolean iscapital;

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

}
