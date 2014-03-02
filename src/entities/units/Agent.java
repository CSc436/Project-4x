package entities.units;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import control.CommandQueue;
import control.UnitType;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class Agent extends Unit {
	private Map<String, Unit> underlings;
	private CommandQueue cq;
	
	public Agent(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, GameObjectType type, UnitType unitType, float xco,
			float yco) {
		super(id, playerId,  baseStats, new_stats, type, unitType, xco, yco);
		underlings = new HashMap<String, Unit>();
		cq = new CommandQueue();
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
