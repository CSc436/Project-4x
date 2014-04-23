package com.server;

import java.util.List;

import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.units.Unit;

/**
 * FourXGameModel - A server-side model which contains all game and player
 * information. Is updated periodically by the Controller class (i.e. this
 * model is not runnable by itself).
 */
public class FourXGameModel {
	
	List<Player> players; // Contains player info and unit/building info
	GameBoard map; // Contains map information
	
	public FourXGameModel( List<Player> players, GameBoard map ) {
		this.players = players;
		this.map = map;
	}

	public boolean getPlayer(Integer i) {
		// TODO Auto-generated method stub
		return false;
	}

	public Unit getUnit(int i) {
		// TODO Auto-generated method stub
		for( Player p : players ) {
			//p.getUnits().
		}
		return null;
	}

}
