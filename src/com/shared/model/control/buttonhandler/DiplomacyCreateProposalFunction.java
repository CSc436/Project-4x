package com.shared.model.control.buttonhandler;

import static com.google.gwt.query.client.GQuery.$;

import java.util.List;

import com.client.GameCanvas;
import com.google.gwt.user.client.Event;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.diplomacy.trading.IntervalResourceTrade;
import com.shared.model.diplomacy.trading.interfaces.ITrade;
import com.shared.model.resources.Resources;

/**
 * Defines behavior for creating a Trade Proposal, after the send button has
 * been clicked.
 * 
 * @author Colin
 * @date Apr 26, 2014
 * 
 */
public class DiplomacyCreateProposalFunction extends AbstractGameModelFunction {

	public DiplomacyCreateProposalFunction(GameCanvas canvas, GameModel model,
			int sendingPlayer) {
		super(canvas, model, sendingPlayer);
	}

	@Override
	public boolean f(Event e) {
		String receivingPlayer = $("#diplomacy-name").get(0).getInnerText();
		String sendAmount = $("#diplomacy-send-quantity").get(0).getInnerText();
		String sendType = $("#diplomacy-send-type").get(0).getInnerText();
		String rcvAmount = $("#diplomacy-receive-quantity").get(0)
				.getInnerText();
		String rcvType = $("#diplomacy-receive-type").get(0).getInnerText();

		int interval = -1;
		int duration = -1;
		try {
			interval = Integer.parseInt($("#diplomacy-time-unit").get(0)
					.getInnerText());
			duration = Integer.parseInt($("#diplomacy-expire").get(0)
					.getInnerText());
		} catch (NumberFormatException nfe) {
			return false;
		}

		// Parse the resources to send/receive.
		Resources send = getResources(sendAmount, sendType);
		Resources receive = getResources(rcvAmount, rcvType);

		// Get the playerId of the receiving player.
		int rcvId = -1;
		List<Player> tempPlayers = model.getPlayers();
		for (Player p : tempPlayers) {
			if (p.getAlias().equals(receivingPlayer)) {
				rcvId = p.getId();
			}
		}
		if (rcvId == -1 || send == null || receive == null) {
			return false;
		}

		ITrade trade = new IntervalResourceTrade(interval, duration,
				sendingPlayer.getId(), rcvId, send, receive);
		model.getTradeManager().createProposal(trade);
		System.out.println("Creating Proposal");
		return true;
	}

	/**
	 * Parses a String resource amount, and a String resource Type into a
	 * Resources Object.
	 * 
	 * @param amount
	 *            - the amount of a resource in a trade.
	 * @param type
	 *            - the type of the resource being traded
	 * @return - a Resources object if parsing succeeded, null otherwise.
	 */
	private Resources getResources(String amount, String type) {
		int amt;
		try {
			amt = Integer.parseInt(amount);
		} catch (NumberFormatException nfe) {
			return null;
		}
		switch (type) {
		case "Gold":
			return new Resources(amt, 0, 0, 0, 0);
		case "Wood":
			return new Resources(0, amt, 0, 0, 0);
		case "Food":
			return new Resources(0, 0, amt, 0, 0);
		case "Stone":
			return new Resources(0, 0, 0, amt, 0);
		default:
			return null;
		}
	}
}
