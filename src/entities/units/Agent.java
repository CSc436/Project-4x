package entities.units;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import control.CommandQueue;
import control.Player;
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
	private int prestige = 0;
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

	public void setPrestige(){
		prestige+=80;
	}
	
	public String getPrestige(){
		if(prestige<100)
			return "hatred";
		else if (prestige>1000)
			return "honored";
		else return "neutral";
	}
	
	public String acceptCommand(int i){
		switch(i){
		case(0):return "nothing";
		case(1):return "no";
		case(2):return "maybe no";
		case(3):return "yes";
		default:return "nothing";
		}
	}
	
	public void actionByPrestige(){
		switch(this.getPrestige()){
		case("hatred"):acceptCommand(1);
		case("neutral"):acceptCommand(2);
		case("honored"):acceptCommand(3);
		default:acceptCommand(0);
		}
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
