package entities;

import com.shared.PhysicsVector;

public interface Movable extends Simulatable {
	
	public PhysicsVector getPosition();
	
	public void setMoveTarget(double x, double y);

}
