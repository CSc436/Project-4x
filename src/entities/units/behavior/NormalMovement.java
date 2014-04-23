package entities.units.behavior;

import com.shared.PhysicsVector;

import control.GameModel;
import entities.units.Unit;
import entities.util.Point;

public class NormalMovement extends MoveBehavior{
	private PhysicsVector targetPosition;
	private PhysicsVector targetVelocity;
	private int maxVelocity = 5;  // these are defaults... can add things later
	private int accel = 3;
	
	public NormalMovement(Point targetLocation) {
		targetPosition = new PhysicsVector(targetLocation);
		targetVelocity = new PhysicsVector(0,0);
	}

	/**
	 * move -
	 * 		Simulates the movement for a specific unit over a single timestep length
	 * 		The result is the units position being shifted closer to the target.
	 * @param m - Unit moving
	 * @param timeStep - unit of time to move in
	 */
	public void move(Unit m, int timeStep) {
		// Get the required fields from Movable object
		PhysicsVector position = m.getUnitPosition();
		PhysicsVector velocity = m.getUnitVelocity();

		/* This code was taken straight verbatum from MovementBehavior in com.shared
		 * Original code by Sean Topping */
		if(position.equals(targetPosition)) return;

		double timeSeconds = timeStep / 1000.0;
		targetVelocity = targetPosition.sub(position).normalize(maxVelocity);
		PhysicsVector prevVelocity = velocity.copy();
		velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);

		double distance = position.sub(targetPosition).magnitude();
		double velMag = velocity.magnitude();
		if( distance <= velMag * velMag / (2 * accel) ) {
			targetVelocity = velocity.normalize(Math.sqrt(2 * distance * accel));
			velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);
		}

		PhysicsVector avgVelocity = prevVelocity.add(velocity).multiply(.5);

		double upper = prevVelocity.magnitude() * timeSeconds + accel * timeSeconds * timeSeconds;
		double lower = prevVelocity.magnitude() * timeSeconds - accel * timeSeconds * timeSeconds;
		if(distance <= upper && distance >= lower) {
			position = targetPosition.copy();
			velocity = new PhysicsVector(0,0);
			targetVelocity = new PhysicsVector(0,0);
		} else {
			position = position.add(avgVelocity.multiply(timeSeconds));
		}

		// Added to make sure the Unit m's position gets updated
		m.setUnitPosition(position.getX(), position.getY());
		m.setUnitVelocity(velocity);
	}


	@Override
	public boolean move(GameModel model, int timeScale) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTargetLocation(Point newTarget) {
		// TODO Auto-generated method stub
		return false;
	}

}
