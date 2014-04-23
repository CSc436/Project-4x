package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Militia extends Unit{

	public Militia(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.MILITIA, BaseStatsEnum.MILITIA.getStats(), UnitType.MILITIA, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
