package entities.units.agents;

import java.util.UUID;

import control.AIControl;
import control.Player;
import entities.units.Agent;

//queueing of commands for controller, controller checks queue each
//time segment, decides how many commands to execute. New commands
//added to end of queue

public class General extends Agent {

	private int range;
	private AIControl brain;

	public General(UUID id, int playerId, int idno) {
		super( id, playerId, BaseStatsEnum baseStats, UnitStats new_stats, GameObjectType type, UnitType unitType, float xco,
				float yco);
		// TODO Auto-generated constructor stub
	}

	public String decision() {
		return "NO";
	}

	// add unit to collection

	// make decision command

	// what options each general has:
	// rally troops, defensive position, attack, move, remove troops
	// add troops
	
	/*
	 * TODO add option to 'patrol' around a city(ies). 
	 * TODO add option to 'explore', causing general to lead troops in random 
	 *      unexplored direction 
	 * TODO add option to 'invade', if enemy city is known, use A*-esque algorithm to 
	 *      lead troops to city and invade.  
	 * TODO add option to 'defend', send troops to ward off enemies at a city (an ally's or your own)
	 */

}
