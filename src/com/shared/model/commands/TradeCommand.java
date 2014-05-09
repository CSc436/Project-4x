package com.shared.model.commands;

import com.shared.model.control.GameModel;
import com.shared.model.diplomacy.trading.TradeFactory;
import com.shared.model.diplomacy.trading.TradeManager;
import com.shared.model.diplomacy.trading.interfaces.ITrade;
import com.shared.model.resources.Resources;

public class TradeCommand implements Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1716348576240409430L;

	public static enum Type {
		ACCEPTPROPOSAL, CREATEPROPOSAL, REJECTPROPOSAL, REJECTACTIVE;
	}

	private int tradeId;
	private Type type;

	public TradeCommand(int tradeId, Type type) {
		this.tradeId = tradeId;
		this.type = type;
	}

	private int totalDuration;
	private int creatingPlayer;
	private int receivingPlayer;
	private Resources p1Trade;
	private Resources p2Trade;

	public TradeCommand(int totalDuration, int creatingPlayer,
			int receivingPlayer, Resources p1Trade, Resources p2Trade) {
		this.totalDuration = totalDuration;
		this.creatingPlayer = creatingPlayer;
		this.receivingPlayer = receivingPlayer;
		this.p1Trade = p1Trade;
		this.p2Trade = p2Trade;
		this.type = Type.CREATEPROPOSAL;
	}

	@Override
	public boolean validateCommand(GameModel model) {
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {
		final TradeManager manager = model.getTradeManager();
		switch (type) {
		case ACCEPTPROPOSAL:
			manager.acceptProposal(tradeId);
			break;
		case CREATEPROPOSAL:
			manager.createProposal(TradeFactory.createIntervalResourceTrade(
					totalDuration, creatingPlayer, receivingPlayer, p1Trade,
					p2Trade));
			break;
		case REJECTPROPOSAL:
			manager.rejectProposal(tradeId);
			break;
		case REJECTACTIVE:
			manager.rejectActive(tradeId);
		}
		return true;
	}

}
