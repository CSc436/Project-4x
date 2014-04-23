package com.shared;

import java.io.Serializable;

public class MovementBehavior implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3598422786758781965L;
	public double maxVelocity; // Movement velocity, in units per second
	public PhysicsVector position;
	public PhysicsVector targetPosition;
	public PhysicsVector velocity = new PhysicsVector(0,0);
	public PhysicsVector targetVelocity = new PhysicsVector(0,0);
	public double accel = 3.0; // Maximum velocity change allowable, in units per second per second
	
	public MovementBehavior() {
		this.position = new PhysicsVector(0,0);
		this.targetPosition = new PhysicsVector(0,0);
		this.maxVelocity = 5;
		this.accel = 5;
	}
	
	public MovementBehavior( PhysicsVector initPosition, double maxVelocity, double accel ) {
		this.position = initPosition.copy();
		this.targetPosition = initPosition.copy();
		this.maxVelocity = maxVelocity;
		this.accel = accel;
	}
	
	public void simulateTimeStep(int timeStep) {
		
		if(position.equals(targetPosition)) return;
		
		double timeSeconds = timeStep / 1000.0;
		targetVelocity = targetPosition.sub(position).normalize(maxVelocity);
		PhysicsVector prevVelocity = velocity.copy();
		velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);
		
		double distance = position.sub(targetPosition).magnitude();
		double velMag = velocity.magnitude();
		if( distance <= velMag * velMag / (2 * accel) ) {
			targetVelocity = velocity.normalize(Math.sqrt(2 * distance * accel));
			velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);
		}
		
		PhysicsVector avgVelocity = prevVelocity.add(velocity).multiply(.5);
		
		double upper = prevVelocity.magnitude() * timeSeconds + accel * timeSeconds * timeSeconds;
		double lower = prevVelocity.magnitude() * timeSeconds - accel * timeSeconds * timeSeconds;
		if(distance <= upper && distance >= lower) {
			position = targetPosition.copy();
			velocity = new PhysicsVector(0,0);
			targetVelocity = new PhysicsVector(0,0);
		} else {
			position = position.add(avgVelocity.multiply(timeSeconds));
		}
	}
	
	public double[] deadReckonPosition( long timeSinceUpdate ) {
		
		double timeSeconds = timeSinceUpdate / 1000.0;
		PhysicsVector tempTargetVelocity = targetPosition.sub(position).normalize(maxVelocity);
		PhysicsVector tempVelocity = velocity.setToTarget(tempTargetVelocity, accel * timeSeconds);
		
		double distance = position.sub(targetPosition).magnitude();
		double velMag = tempVelocity.magnitude();
		if( distance <= velMag * velMag / (2 * accel) ) {
			tempTargetVelocity = tempVelocity.normalize(Math.sqrt(2 * distance * accel));
			tempVelocity = tempVelocity.setToTarget(tempTargetVelocity, accel * timeSeconds);
		}
		
		PhysicsVector tempPosition = position.add(tempVelocity.multiply(timeSeconds));
		
		double[] positionArray = new double[2];
		positionArray[0] = tempPosition.getX();
		positionArray[1] = tempPosition.getY();
		
		return positionArray;
		
	}
	
	public void setTarget(double x, double y) {
		targetPosition.set(x, y);
	}
}
