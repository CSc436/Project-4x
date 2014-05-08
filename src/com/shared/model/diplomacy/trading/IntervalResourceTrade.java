package com.shared.model.diplomacy.trading;

import com.shared.model.control.Player;
import com.shared.model.resources.Resources;

/**
 * The Implementation for an Trade that occurs on a regular interval involving
 * Resources.
 * 
 * @author Colin
 * @date Apr 3, 2014
 * 
 */
public class IntervalResourceTrade extends AbstractIntervalTrade {
	private static final long serialVersionUID = 1986949014291109777L;

	private int creatingPlayer;
	private int receivingPlayer;
	private Resources p1Trade;
	private Resources p2Trade;
	
	private int id;

	/**
	 * Initializes the interval trade.
	 * 
	 * @param totalDuration
	 *            - the total time until the trade deactivates.
	 * @param creatingPlayer
	 *            - the player.id that created the trade
	 * @param receivingPlayer
	 *            - the player.id that is the receiver of the trade agreement.
	 * @param p1Trade
	 *            - The Resources that player1 gives to player2 on each act().
	 * @param p2Trade
	 *            - The Resources that player2 gives to player1 on each act().
	 * @param id - the id of this trade 
	 */
	public IntervalResourceTrade(int totalDuration, int creatingPlayer,
			int receivingPlayer, Resources p1Trade, Resources p2Trade, int id) {
		super(totalDuration);
		this.creatingPlayer = creatingPlayer;
		this.receivingPlayer = receivingPlayer;
		this.p1Trade = p1Trade;
		this.p2Trade = p2Trade;
	}

	/**
	 * Performs a Resource Trade between the two players.
	 * 
	 * @param p1
	 *            - The Player Instance of the initiating player.
	 * @param p2
	 *            - the Player Instance of the receiving player.
	 */
	@Override
	public void act(Player p1, Player p2) {
		if (p1.resources.can_spend(p1Trade) && p2.resources.can_spend(p2Trade)) {
			p1.resources.spend(p1Trade);
			p2.resources.receive(p1Trade);

			p2.resources.spend(p2Trade);
			p1.resources.receive(p2Trade);
		}
	}

	@Override
	public int getPlayer1() {
		return creatingPlayer;
	}

	@Override
	public int getPlayer2() {
		return receivingPlayer;
	}

	public int getCreatingPlayer() {
		return creatingPlayer;
	}

	public int getReceivingPlayer() {
		return receivingPlayer;
	}

	public Resources getP1Trade() {
		return p1Trade;
	}

	public Resources getP2Trade() {
		return p2Trade;
	}
	
	@Override
	public int getId() {
		return id;
	}
}
