package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.pawns.Trade_Cart;

public class Town_Hall extends ResourceBuilding {

	Trade_Cart trader;
	boolean iscapital;

	/*
	 * trading : 1 trade cart per town hall, each trade cart will autonomously
	 * go from the capital to its designated trading city. This will be the same
	 * for allies, perhaps a bonus for trading with the allies capital city
	 */

	public Town_Hall(int id, int playerId, float xco, float yco, boolean iscapital) {
		super(id, playerId, BaseStatsEnum.TOWN_HALL, BaseStatsEnum.TOWN_HALL.getStats(), 
				BuildingType.TOWN_HALL,
				xco, yco, 4, 4, new Resources(0, 0, 0, 0, 0));

		this.iscapital = iscapital;

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
