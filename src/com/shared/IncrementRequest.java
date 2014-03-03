package com.shared;

import com.server.Model;

public class IncrementRequest extends Request {
	
	private IncrementRequest() {
		super();
	}
	
	public static Request generateRequest(int scheduledTurn, int lastTurnReceived) {
		Request r = Request.generateRequest(scheduledTurn, lastTurnReceived);
		
		return r;
	}
	
	public boolean executeOn(Model m) {
		if(m.canIncrement()) {
			m.increment();
			return true;
		} else
			return false;
	}

}
