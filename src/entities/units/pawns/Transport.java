package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Transport extends Unit {

	public Transport(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.TRANSPORT, BaseStatsEnum.TRANSPORT.getStats(), UnitType.TRANSPORT, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
