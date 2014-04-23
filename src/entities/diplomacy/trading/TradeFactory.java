package entities.diplomacy.trading;

import entities.diplomacy.trading.interfaces.ITrade;
import entities.resources.Resources;

public class TradeFactory {
	public static ITrade createIntervalResourceTrade(float interval,
			float totalDuration, int creatingPlayer, int receivingPlayer,
			Resources p1Trade, Resources p2Trade) {
		return new IntervalResourceTrade(interval, totalDuration,
				creatingPlayer, receivingPlayer, p1Trade, p2Trade);
	}
}
