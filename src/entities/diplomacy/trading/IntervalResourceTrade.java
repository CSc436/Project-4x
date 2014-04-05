package entities.diplomacy.trading;

import control.Player;
import entities.resources.Resources;

/**
 * The Implementation for an Trade that occurs on a regular interval involving
 * Resources.
 * 
 * @author Colin
 * @date Apr 3, 2014
 * 
 */
public class IntervalResourceTrade extends AbstractIntervalTrade {
	private int creatingPlayer;
	private int receivingPlayer;
	private Resources p1Trade;
	private Resources p2Trade;

	/**
	 * Initializes the interval trade.
	 * 
	 * @param interval
	 *            - how often in seconds to perform a trade.
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
	 */
	public IntervalResourceTrade(float interval, float totalDuration,
			int creatingPlayer, int receivingPlayer, Resources p1Trade,
			Resources p2Trade) {
		super(interval, totalDuration);
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
}
