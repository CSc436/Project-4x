package com.shared.model.behaviors;

import java.io.Serializable;

public interface Attackable extends Serializable {
	
	public void simulateDamage( int timeStep );
	
	public void takeDamage( int damage );
	
	public boolean isDead();

	public float getHealthPercentage();
	
}
