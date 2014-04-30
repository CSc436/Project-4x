package com.shared.model.diplomacy.trading;

import java.io.Serializable;

import com.shared.model.diplomacy.trading.interfaces.ITrade;
import com.shared.model.resources.Resources;

public class TradeFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -469678092884051373L;

	public static ITrade createIntervalResourceTrade(float interval,
			float totalDuration, int creatingPlayer, int receivingPlayer,
			Resources p1Trade, Resources p2Trade) {
		return new IntervalResourceTrade(interval, totalDuration,
				creatingPlayer, receivingPlayer, p1Trade, p2Trade);
	}
}
