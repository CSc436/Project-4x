package entities;

import java.io.Serializable;

public interface Simulatable extends Serializable {
	
	public void advanceTimeStep( int timeStep );
	
}
