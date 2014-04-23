package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Cannon extends Unit{

	public Cannon(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.CANNON, BaseStatsEnum.CANNON.getStats(), UnitType.CANNON, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
