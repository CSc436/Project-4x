package com.shared.model.diplomacy.trading.interfaces;

import java.io.Serializable;

import com.shared.model.control.Player;

/**
 * A Trade Agreement between two players. This interface provides the necessary
 * methods for the gameloop to do something given the agreement
 * 
 * @author Colin
 * @date Apr 3, 2014
 * 
 */
public interface ITrade extends Serializable {
	/**
	 * Timestep updating of a Trade Agreement between two players, returns
	 * whether to act on the agreement during this game step.
	 * 
	 * @param delta
	 *            - the amount of time passed since the last update
	 * @return - whether an action should be taken for this agreement.
	 */
	public boolean update(float delta);

	/**
	 * Act on an agreement made between two players
	 * 
	 * @param p1
	 *            - the first player of the agreement
	 * @param p2
	 *            - the second player of the agreement
	 */
	public void act(Player p1, Player p2);

	/**
	 * Whether the trade is to be removed from the active trades list.
	 * 
	 * @return - if the trade is active.
	 */
	public boolean isActive();

	/**
	 * Gets the player initiating the trade
	 * 
	 * @return - the id of the first player.
	 */
	public int getCreatingPlayer();

	/**
	 * Gets the player on the receiving end of the trade
	 * 
	 * @return - the id of the second player.
	 */
	public int getReceivingPlayer();
	
	/**
	 * 
	 * @return - the auto-generated Id of the trade.
	 */
	public int getId();

}
