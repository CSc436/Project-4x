package com.shared.model;




public class SetTargetRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4338389460462282941L;
	public int entityID;
	public double x;
	public double y;
	
	public SetTargetRequest() {
		super();
		entityID = 0;
		this.x = 0.0;
		this.y = 0.0;
	}
	
	public SetTargetRequest(int entityID, double x, double y) {
		super();
		this.entityID = entityID;
		this.x = x;
		this.y = y;
	}
	
	public boolean executeOn(SimpleGameModel m) {
		m.get(entityID).getMovementBehavior().setTarget(x, y);
		return true;
	}

}
