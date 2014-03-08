package com.shared;

import java.io.Serializable;

import com.fourx.util.Point;

public class MovingUnit implements Serializable {
	public double maxVelocity; // Movement velocity, in units per second
	public PhysicsVector position;
	public PhysicsVector targetPosition;
	public PhysicsVector velocity = new PhysicsVector(0,0);
	public PhysicsVector targetVelocity = new PhysicsVector(0,0);
	public double accel = 3.0; // Maximum velocity change allowable, in units per second per second
	public int turnNumber;
	
	public MovingUnit() {
		position = new PhysicsVector(0,0);
		targetPosition = new PhysicsVector(0,0);
		maxVelocity = 1;
		turnNumber = 0;
	}
	
	public MovingUnit(double x, double y, double vel) {
		position = new PhysicsVector(x,y);
		targetPosition = new PhysicsVector(x,y);
		maxVelocity = vel;
		accel = vel;
		turnNumber = 0;
	}
	
	public void simulateTimeStep(int timeStep) {
		double timeSeconds = timeStep / 1000.0;
		targetVelocity = targetPosition.sub(position).normalize(maxVelocity);
		velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);
		
		double distance = position.sub(targetPosition).magnitude();
		double velMag = velocity.magnitude();
		if( distance <= velMag * velMag / (2 * accel) ) {
			targetVelocity = velocity.normalize(Math.sqrt(2 * distance * accel));
			velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);
		}
		
		position = position.add(velocity.multiply(timeSeconds));
	}
	
	public double[] deadReckonPosition( long timeSinceUpdate ) {
		
		double timeSeconds = timeSinceUpdate / 1000.0;
		PhysicsVector tempTargetVelocity = targetPosition.sub(position).normalize(maxVelocity);
		PhysicsVector tempVelocity = velocity.setToTarget(tempTargetVelocity, accel * timeSeconds);
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
