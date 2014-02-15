package com.shared;


public class DecrementRequest extends Request {
	
	private DecrementRequest() {
		super();
	}
	
	public static DecrementRequest generateRequest(int scheduledTurn, int lastTurnReceived) {
		DecrementRequest r = new DecrementRequest();
		r.setScheduledTurn(scheduledTurn);
		r.setLastTurnReceived(lastTurnReceived);
		
		return r;
	}
	
	@Override
	public boolean executeOn(Model m) {
		if(m.canDecrement()) {
			m.decrement();
			return true;
		} else
			return false;
	}

}
