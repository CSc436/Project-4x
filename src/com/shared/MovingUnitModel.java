package com.shared;

public class MovingUnitModel extends EntityModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036643547617800286L;
	
	public MovingUnitModel() {
		movementBehavior = new MovementBehavior( new PhysicsVector(0,0), 1, 3 );
	}
	
	public MovingUnitModel( double initX, double initY, double maxVel, double maxAccel ) {
		movementBehavior = new MovementBehavior( new PhysicsVector( initX, initY ), maxVel, maxAccel );
	}
	
	public void simulateTimeStep(int timeStep) {
		movementBehavior.simulateTimeStep(timeStep);
	}
	
	public double[] deadReckonPosition( long timeSinceUpdate ) {
		return movementBehavior.deadReckonPosition(timeSinceUpdate);
	}
	
	public void setTarget(double x, double y) {
		movementBehavior.setTarget(x, y);
	}
}
