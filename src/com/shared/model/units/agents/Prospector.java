package com.shared.model.units.agents;

import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Agent;
import com.shared.model.units.UnitType;

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

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3335242018122152581L;

	/**
	 * General():
	 * @param id       - unique id for this Prospector object
	 * @param playerId - id of player that owns this general
	 * @param UnitStasts stats - stats this unit should currently have. 
	 * @param xco  - initial x coordinate on gameboard of Prospector object. 
	 * @param yco  - initial y coordinate on gameboard of Prospector object. 
	 */
	public Prospector(int id, int playerid, float xco, float yco) {
		super(id,  playerid, BaseStatsEnum.PROSPECTOR, BaseStatsEnum.PROSPECTOR.getStats(), null, UnitType.PROSPECTOR, xco,
			 yco);
		// TODO Auto-generated constructor stub
	}
	
	public Prospector() {}

	@Override
	public void update(Player p, GameBoard gb) {
		// TODO Auto-generated method stub
		
	}


	
	/*
	 * TODO use an A*-esque algorithm to find path to desired resource. Once path is found (location of tile)
	 * Send villagers to mine resource. Villagers may have to build new building etc.  
	 */
	
	
}
