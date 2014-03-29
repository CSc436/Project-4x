package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.GameObjectType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.pawns.Trade_Cart;

public class Town_Hall extends ResourceBuilding {

	Trade_Cart trader;
	boolean iscapital;

	/*
	 * trading : 1 trade cart per town hall, each trade cart will autonomously
	 * go from the capital to its designated trading city. This will be the same
	 * for allies, perhaps a bonus for trading with the allies capital city
	 */

	public Town_Hall(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, GameObjectType gameObjectType,
			BuildingType buildingType, float xco, float yco, int height,
			int width, boolean iscapital) {
		super(id, playerId, baseStats, new_stats, gameObjectType, buildingType,
				xco, yco, height, width, new Resources(0, 0, 0, 0));

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
