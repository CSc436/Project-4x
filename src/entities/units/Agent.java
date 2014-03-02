package entities.units;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import control.CommandQueue;
import control.Player;
import entities.UnitType;
import entities.stats.BaseStatsEnum;

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
	 * @param Player p - the player that owns this agent 
	 * @param int idno - id number for this unit. 
	 */
	public Agent(Player p, int idno) {
		super(p, BaseStatsEnum.FOOTMAN, UnitType.INFANTRY, 0, 0, idno);
		underlings = new HashMap<String, Unit>();
		cq = new CommandQueue();
	}
}
