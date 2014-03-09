package entities.units.agents;

import control.Player;
import entities.units.Agent;

/**
 * 
 * @author NRTopping
 * @class Prospector
 * @summary 
 * The prospector is an agent that controls serfs/towns people and 
 * directs them to collect resources in nearby areas. The prospector
 * will set out and explore for more resources if the surrounding area
 * becomes depleted. 
 * The prospector will have the ability - if the player so chooses - to
 * build resource generating buildings. 
 */
public class Prospector extends Agent{

	
	public Prospector(Player p, int idno) {
		super(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, GameObjectType type, UnitType unitType, float xco,
				float yco);
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * TODO use an A*-esque algorithm to find path to desired resource. Once path is found (location of tile)
	 * Send villagers to mine resource. Villagers may have to build new building etc.  
	 */
}
