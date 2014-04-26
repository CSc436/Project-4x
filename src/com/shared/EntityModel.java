package com.shared;

import java.io.Serializable;

import entities.behaviors.MoveBehavior;

public abstract class EntityModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6408658237353388571L;
	protected MoveBehavior movementBehavior;
	
	public EntityModel() {
		movementBehavior = new MoveBehavior( new PhysicsVector(0,0), 5, 5 );
	}
	
	public MoveBehavior getMovementBehavior() {
		return movementBehavior;
	}
	
}
