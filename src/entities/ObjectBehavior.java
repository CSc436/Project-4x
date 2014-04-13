package entities;

import java.io.Serializable;

public interface ObjectBehavior extends Serializable {
	
	/**
	 * Simulate the object behavior for a certain amount of time
	 * @param timeStep - Number of milliseconds to simulate behavior
	 */
	public void advanceTimeStep( int timeStep );
	
}
