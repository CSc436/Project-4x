package com.shared.model.control.buttonhandler;

import com.client.GameCanvas;
import com.google.gwt.query.client.Function;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;

public abstract class AbstractGameModelFunction extends Function {
	protected final GameCanvas canvas;
	protected final GameModel model;
	protected final Player sendingPlayer;
	
	public AbstractGameModelFunction(final GameCanvas canvas, final GameModel model, final int sendingPlayer) {
		super();
		this.canvas = canvas;
		this.model = model;
		this.sendingPlayer = model.getPlayer(sendingPlayer);
	}
}
