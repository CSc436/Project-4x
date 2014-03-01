package com.shared;


public class SetTargetRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4338389460462282941L;
	double x;
	double y;
	
	public SetTargetRequest() {
		super();
		this.x = 0.0;
		this.y = 0.0;
	}
	
	public SetTargetRequest(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public boolean executeOn(Model m) {
		m.setTarget(x, y);
		return true;
	}

}
