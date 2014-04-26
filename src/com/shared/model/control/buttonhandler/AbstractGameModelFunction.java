package com.shared.model.control.buttonhandler;

import com.client.GameCanvas;
import com.google.gwt.query.client.Function;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;

/**
 * An Abstract class that allows us to define a client side function that has
 * some standard variables available to it. This class is to be extended for
 * specialized behavior, and allows us to abstract functionality from the
 * GameCanvas (view) class.
 * 
 * @author Colin
 * @date Apr 26, 2014
 * 
 */
public abstract class AbstractGameModelFunction extends Function {
	protected final GameCanvas canvas;
	protected final GameModel model;
	protected final Player sendingPlayer;

	/**
	 * Default constructor
	 * 
	 * @param canvas
	 *            - the gameCanvas, for when we want to modify the view when an
	 *            action happens.
	 * @param model
	 *            - the GameModel with which we get all of our data from.
	 * @param sendingPlayer
	 *            - the playerId that is converted to an actual Player instance
	 *            with the above model.
	 */
	public AbstractGameModelFunction(final GameCanvas canvas,
			final GameModel model, final int sendingPlayer) {
		super();
		this.canvas = canvas;
		this.model = model;
		this.sendingPlayer = model.getPlayer(sendingPlayer);
	}
}
