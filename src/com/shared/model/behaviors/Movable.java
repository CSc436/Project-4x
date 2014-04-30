package com.shared.model.behaviors;

import java.io.Serializable;

import com.shared.utils.PhysicsVector;

public interface Movable extends Serializable {
	
	public void simulateMovement( int timeStep );
	
	public PhysicsVector extrapolatePosition( int timeStep );
	
	public PhysicsVector getPosition();
	
	public void setMoveTarget(double x, double y);

}
