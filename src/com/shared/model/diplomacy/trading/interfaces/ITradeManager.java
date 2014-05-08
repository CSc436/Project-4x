package com.shared.model.diplomacy.trading.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * The TradeManager - Manages Trading with other players. (Accepting, Rejecting
 * and Running Active Trades)
 * 
 * @author Colin
 * @date Apr 3, 2014
 * 
 */
public interface ITradeManager extends Serializable {
	/**
	 * This method allows for defining how to update Trades that the
	 * ITradeManager manages.
	 * 
	 * @param delta
	 *            - the time step in the gameloop in seconds
	 */
	public void update(float delta);

	/**
	 * 
	 * @param playerId
	 *            - the player.id
	 * @return - a List of all trades that this player is a part of.
	 */
	public List<ITrade> getAcceptedTrades(int playerId);

	/**
	 * 
	 * @param playerId
	 *            - the player.id
	 * @return - a List of all trades that this player has sent to others.
	 */
	public List<ITrade> getSentTrades(int playerId);

	/**
	 * 
	 * @param playerId
	 *            - the player.id
	 * @return - a List of all trades that this player has received from others.
	 */
	public List<ITrade> getReceivedTrades(int playerId);

	/**
	 * 
	 * @param proposal
	 *            - the Trade that we want to add to the List of managed trade
	 */
	public void createProposal(ITrade proposal);

	/**
	 * 
	 * @param proposal
	 *            - the Trade proposal we have received that we are accepting.
	 */
	public void acceptProposal(int proposalId);

	/**
	 * 
	 * @param proposal
	 *            - the Trade proposal we have received that we are rejecting.
	 */
	public void rejectProposal(int proposalId);

	/**
	 * 
	 * @param trade
	 *            - the currently active trade to disable and remove.
	 */
	public void rejectActive(int tradeId);
}
