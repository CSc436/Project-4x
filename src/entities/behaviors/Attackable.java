package entities.behaviors;

import java.io.Serializable;

public interface Attackable extends Serializable {
	
	public void simulateDamage( int timeStep );
	
	public void takeDamage( int damage );
	
	public boolean isDead();
	
}
