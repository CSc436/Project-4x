package com.shared;

import java.io.Serializable;

public abstract class EntityModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6408658237353388571L;
	protected MovementBehavior movementBehavior;
	
	public EntityModel() {
		movementBehavior = new MovementBehavior( new PhysicsVector(0,0), 5, 5 );
	}
	
	public MovementBehavior getMovementBehavior() {
		return movementBehavior;
	}
	
}
