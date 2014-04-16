package com.shared;

import entities.MoveBehavior;

public class MovingUnitModel extends EntityModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036643547617800286L;
	
	public MovingUnitModel() {
		movementBehavior = new MoveBehavior( new PhysicsVector(0,0), 1, 3 );
	}
	
	public MovingUnitModel( double initX, double initY, double maxVel, double maxAccel ) {
		movementBehavior = new MoveBehavior( new PhysicsVector( initX, initY ), maxVel, maxAccel );
	}
	
	public void simulateTimeStep(int timeStep) {
		movementBehavior.advanceTimeStep(timeStep);
	}
	
	public double[] deadReckonPosition( long timeSinceUpdate ) {
		return movementBehavior.extrapolatePosition(timeSinceUpdate);
	}
	
	public void setTarget(double x, double y) {
		movementBehavior.setTarget(x, y);
	}
}
