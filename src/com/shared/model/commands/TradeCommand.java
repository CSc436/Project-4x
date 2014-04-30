package com.shared.model.commands;

import com.shared.model.control.GameModel;
import com.shared.model.diplomacy.trading.TradeManager;
import com.shared.model.diplomacy.trading.interfaces.ITrade;

public class TradeCommand implements Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1716348576240409430L;

	public static enum Type {
		ACCEPTPROPOSAL, CREATEPROPOSAL, REJECTPROPOSAL, REJECTACTIVE;
	}
	
	private final ITrade trade;
	private final Type type;
	
	public TradeCommand(final ITrade trade, final Type type) {
		this.trade = trade;
		this.type = type;
	}
	
	public TradeCommand() {
		this.trade = null;
		this.type = null;
	}

	@Override
	public boolean validateCommand(GameModel model) {
		return false;
	}

	@Override
	public boolean performCommand(GameModel model) {
		final TradeManager manager = model.getTradeManager();
		switch(type) {
		case ACCEPTPROPOSAL:
			manager.acceptProposal(trade);
			break;
		case CREATEPROPOSAL:
			manager.createProposal(trade);
			break;
		case REJECTPROPOSAL:
			manager.rejectProposal(trade);
			break;
		case REJECTACTIVE:
			manager.rejectActive(trade);
		}
		return false;
	}

}
