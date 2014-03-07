package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import control.UnitType;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Unit;

public class Trade_Cart extends Unit{

	public Trade_Cart(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, GameObjectType type, UnitType unitType,
			float xco, float yco) {
		super(id, playerId, baseStats, new_stats, type, unitType, xco, yco);
		// TODO Auto-generated constructor stub
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
