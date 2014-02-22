package com.shared;

import java.io.Serializable;

public class Request implements Serializable {
	
	private int scheduledTurn;
	private int lastTurnReceived;
	
	protected Request() {
		scheduledTurn = 0;
		lastTurnReceived = 0;
	}
	
	public void setScheduledTurn(int scheduledTurn) {
		this.scheduledTurn = scheduledTurn;
	}
	
	public void setLastTurnReceived(int lastTurnReceived) {
		this.lastTurnReceived = lastTurnReceived;
	}	
	
	public int getScheduledTurn() {
		return scheduledTurn;
	}

	public int getLastTurnReceived() {
		return lastTurnReceived;
	}

	public static Request generateRequest(int scheduledTurn, int lastTurnReceived) {
		Request r = new Request();
		r.setScheduledTurn(scheduledTurn);
		r.setLastTurnReceived(lastTurnReceived);
		
		return r;
	}
	
	public boolean executeOn(Model m) {
		return false;
	}
	
}
