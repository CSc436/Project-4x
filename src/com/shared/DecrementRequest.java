package com.shared;


public class DecrementRequest extends Request {
	
	private DecrementRequest() {
		super();
	}
	
	public static Request generateRequest(int scheduledTurn, int lastTurnReceived) {
		Request r = Request.generateRequest(scheduledTurn, lastTurnReceived);
		
		return r;
	}
	
	public boolean executeOn(Model m) {
		if(m.canDecrement()) {
			m.decrement();
			return true;
		} else
			return false;
	}

}
