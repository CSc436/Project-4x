package com.shared.model.diplomacy.trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.shared.model.control.GameModel;
import com.shared.model.diplomacy.trading.interfaces.ITrade;
import com.shared.model.diplomacy.trading.interfaces.ITradeManager;

/**
 * This class lives at the GameModel level, and update gets called each update
 * cycle to check if any trades should be performed.
 * 
 * @author Colin
 * @date Apr 3, 2014
 * 
 */
public class TradeManager implements ITradeManager {
	private static final long serialVersionUID = -7693455599294524010L;
	private Map<Integer, ITrade> proposedTrades;
	private Map<Integer, ITrade> activeTrades;

	/** The GameModel object with which we get the player instances with */
	private GameModel game;

	public TradeManager(GameModel game) {
		this.game = game;
		proposedTrades = new HashMap<Integer, ITrade>();
		activeTrades = new HashMap<Integer, ITrade>();
	}
	
	public TradeManager() {}

	@Override
	public void update(float delta) {
		ArrayList<ITrade> removables = new ArrayList<ITrade>();
		for (Entry<Integer, ITrade> entry : activeTrades.entrySet()) {
			ITrade trade = entry.getValue();
			if (!trade.isActive()) {
				removables.add(trade);
			} else if (trade.update(delta)) {
				trade.act(game.getPlayer(trade.getPlayer1()),
						game.getPlayer(trade.getPlayer2()));
			}
		}
		for (ITrade remove : removables) {
			activeTrades.remove(remove);
		}
	}

	@Override
	public List<ITrade> getAcceptedTrades(int playerId) {
		List<ITrade> results = new ArrayList<ITrade>();
		for (Entry<Integer, ITrade> entry : activeTrades.entrySet()) {
			ITrade trade = entry.getValue();
			if (trade.getPlayer1() == playerId
					|| trade.getPlayer2() == playerId) {
				results.add(trade);
			}
		}
		return results;
	}

	@Override
	public List<ITrade> getSentTrades(int playerId) {
		List<ITrade> results = new ArrayList<ITrade>();
		for (Entry<Integer, ITrade> entry : proposedTrades.entrySet()) {
			ITrade trade = entry.getValue();
			if (trade.getPlayer1() == playerId) {
				results.add(trade);
			}
		}
		return results;
	}

	@Override
	public List<ITrade> getReceivedTrades(int playerId) {
		List<ITrade> results = new ArrayList<ITrade>();
		for (Entry<Integer, ITrade> entry : proposedTrades.entrySet()) {
			ITrade trade = entry.getValue();
			if (trade.getPlayer2() == playerId) {
				results.add(trade);
			}
		}
		return results;
	}

	@Override
	public void createProposal(ITrade proposal) {
		proposedTrades.put(proposal.getId(), proposal);
	}

	@Override
	public void acceptProposal(int proposalId) {
		ITrade trade = proposedTrades.remove(proposalId);
		activeTrades.put(trade.getId(), trade);
	}

	@Override
	public void rejectProposal(int proposalId) {
		proposedTrades.remove(proposalId);
	}

	@Override
	public void rejectActive(int tradeId) {
		activeTrades.remove(tradeId);
	}
}
