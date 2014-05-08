package com.shared.model.diplomacy.trading;

import com.shared.model.control.Player;
import com.shared.model.diplomacy.trading.interfaces.ITrade;

/**
 * This abstract class allows for creating a Trade that is to be executed every
 * {interval} time steps for {timeRemaining} time.
 * 
 * @author Colin
 * @date Apr 3, 2014
 * 
 */
public abstract class AbstractIntervalTrade implements ITrade {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8631614298939652590L;
	private float currentTime;
	private static final float interval = 30;
	private float timeRemaining;

	/**
	 * Constructor that initializes this Trade with a trade interval and total
	 * duration.
	 * 
	 * @param totalDuration
	 *            - the total amount of time this IntervalTrade is active in minutes.
	 */
	protected AbstractIntervalTrade(int totalDuration) {
		currentTime = 0;
		this.timeRemaining = totalDuration * 60;
	}

	/**
	 * @return boolean - returns whether the trade should be executed this turn
	 *         based on the currentTime since the trade was created.
	 */
	public boolean update(float delta) {
		currentTime += delta;
		timeRemaining -= delta;
		if (currentTime > interval) {
			currentTime -= interval;
			return true;
		}
		return false;
	}

	public abstract void act(Player p1, Player p2);

	/**
	 * @return boolean - this Trade becomes inactive when timeRemaining reaches
	 *         0.
	 */
	public boolean isActive() {
		return timeRemaining >= 0;
	}

	public abstract int getPlayer1();

	public abstract int getPlayer2();
	
	public abstract int getId();
}
