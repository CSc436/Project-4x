package com.shared.model.diplomacy.trading;

import java.io.Serializable;

import com.shared.model.diplomacy.trading.interfaces.ITrade;
import com.shared.model.resources.Resources;

public class TradeFactory implements Serializable {
	private static final long serialVersionUID = -469678092884051373L;

	private static int counter = 0;

	/**
	 * The parameters should be passed in through an RPC call, and the server
	 * should handle creating them and adding them to the TradeManager.
	 * 
	 * @param totalDuration - the duration of the trade in minutes.
	 * @param creatingPlayer - the id of the creating player.
	 * @param receivingPlayer - the id of the receiving player.
	 * @param p1Trade - the resources p1 wants to trade.
	 * @param p2Trade - the resources p2 will trade.
	 * @return - the created Trade which is to be added by the TradeManager.
	 */
	public static ITrade createIntervalResourceTrade(int totalDuration,
			int creatingPlayer, int receivingPlayer, Resources p1Trade,
			Resources p2Trade) {
		return new IntervalResourceTrade(totalDuration, creatingPlayer,
				receivingPlayer, p1Trade, p2Trade, counter++);
	}
}
