package com.shared;


public class IncrementRequest extends Request {
	
	private IncrementRequest() {
		super();
	}
	
	public static IncrementRequest generateRequest(int scheduledTurn, int lastTurnReceived) {
		IncrementRequest r = new IncrementRequest();
		r.setScheduledTurn(scheduledTurn);
		r.setLastTurnReceived(lastTurnReceived);
		
		return r;
	}
	
	@Override
	public boolean executeOn(Model m) {
		if(m.canIncrement()) {
			m.increment();
			return true;
		} else
			return false;
	}

}
