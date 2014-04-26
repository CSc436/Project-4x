package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Transport extends Unit {

	public Transport(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.TRANSPORT, BaseStatsEnum.TRANSPORT.getStats(), UnitType.TRANSPORT, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
