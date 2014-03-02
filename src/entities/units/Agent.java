package entities.units;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import control.CommandQueue;
import control.UnitType;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

/**
 * 
 * @author NRTopping
 * @class Agent
 * @summary 
 * This class extends the Unit object. Agents act at their own digression, based on commands
 * given to by players. Agent class is not meant to be created on own, only classes that implement
 * Agent (hence why it is abstract). 
 * 
 */
public abstract class Agent extends Unit {
	private Map<String, Unit> underlings; // TODO possibly replace Unit or String with their unique ID's (UUID?)
	private CommandQueue cq; // list of current commnands to issue.

	/*
	 * Agent():
	 * Description:
	 * Base constructor for and Agent object. Calls the Unit constructor, 
	 * initializes underlings, and Command Queue. 
	 * 
	 * Parameters:
	 */
	
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
