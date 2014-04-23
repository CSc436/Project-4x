package entities.diplomacy.trading;

import java.util.ArrayList;
import java.util.List;

import control.GameModel;
import entities.diplomacy.trading.interfaces.ITrade;
import entities.diplomacy.trading.interfaces.ITradeManager;

/**
 * This class lives at the GameModel level, and update gets called each update
 * cycle to check if any trades should be performed.
 * 
 * @author Colin
 * @date Apr 3, 2014
 * 
 */
public class TradeManager implements ITradeManager {
	private List<ITrade> proposedTrades;
	private List<ITrade> activeTrades;

	/** The GameModel object with which we get the player instances with */
	private GameModel game;

	public TradeManager(GameModel game) {
		this.game = game;
		proposedTrades = new ArrayList<ITrade>();
		activeTrades = new ArrayList<ITrade>();
	}

	@Override
	public void update(float delta) {
		ArrayList<ITrade> removables = new ArrayList<ITrade>();
		for (ITrade trade : activeTrades) {
			if (!trade.isActive()) {
				removables.add(trade);
			} else if (trade.update(delta)) {
				trade.act(game.getPlayer(trade.getPlayer1()),
						game.getPlayer(trade.getPlayer2()));
			}
		}
		for(ITrade remove: removables) {
			activeTrades.remove(remove);
		}
	}

	@Override
	public List<ITrade> getAcceptedTrades(int playerId) {
		List<ITrade> results = new ArrayList<ITrade>();
		for (ITrade trade : activeTrades) {
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
		for (ITrade trade : proposedTrades) {
			if (trade.getPlayer1() == playerId) {
				results.add(trade);
			}
		}
		return results;
	}

	@Override
	public List<ITrade> getReceivedTrades(int playerId) {
		List<ITrade> results = new ArrayList<ITrade>();
		for (ITrade trade : proposedTrades) {
			if (trade.getPlayer2() == playerId) {
				results.add(trade);
			}
		}
		return results;
	}

	@Override
	public void createProposal(ITrade proposal) {
		proposedTrades.add(proposal);
	}

	@Override
	public void acceptProposal(ITrade proposal) {
		proposedTrades.remove(proposal);
		activeTrades.add(proposal);
	}

	@Override
	public void rejectProposal(ITrade proposal) {
		proposedTrades.remove(proposal);
	}

}
