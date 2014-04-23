package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Knight extends Unit{

	public Knight(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.KNIGHT, BaseStatsEnum.KNIGHT.getStats(), UnitType.KNIGHT, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
