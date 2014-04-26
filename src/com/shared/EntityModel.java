package com.shared;

import java.io.Serializable;

import entities.behaviors.StandardMover;

public abstract class EntityModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6408658237353388571L;
	protected StandardMover movementBehavior;
	
	public EntityModel() {
		movementBehavior = new StandardMover( new PhysicsVector(0,0), 5, 5 );
	}
	
	public StandardMover getMovementBehavior() {
		return movementBehavior;
	}
	
}
