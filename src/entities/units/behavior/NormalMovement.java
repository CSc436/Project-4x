package entities.units.behavior;

import java.util.Stack;

import com.shared.PhysicsVector;

import control.GameModel;
import entities.units.Unit;
import entities.util.Point;

public class NormalMovement extends MoveBehavior{
	
	private Stack<PhysicsVector> targetPosition;
	private PhysicsVector targetVelocity;
	private PhysicsVector position;
	private PhysicsVector velocity;
	private int maxVelocity = 5;  // these are defaults... can add things later
	private int accel = 3;
	
	public NormalMovement(Unit m, Point targetLocation) {
		targetPosition = new Stack<PhysicsVector>();
		targetPosition.push(new PhysicsVector(targetLocation));
		targetVelocity = new PhysicsVector(0,0);
		position = m.getUnitPosition();
		velocity = m.getUnitVelocity();
		
	}

	/**
	 * move -
	 * 		Simulates the movement for a specific unit over a single timestep length
	 * 		The result is the units position being shifted closer to the target.
	 * @param m - Unit moving
	 * @param timeStep - unit of time to move in
	 */
	@Override
	public void move(int timeStep) {
		// Get the required fields from Movable object

		/* This code was taken straight verbatum from MovementBehavior in com.shared
		 * Original code by Sean Topping */
		if(position.equals(targetPosition))
		{
			targetPosition.pop();
			return;
		}

		double timeSeconds = timeStep / 1000.0;
		targetVelocity = targetPosition.peek().sub(position).normalize(maxVelocity);
		PhysicsVector prevVelocity = velocity.copy();
		velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);

		double distance = position.sub(targetPosition.peek()).magnitude();
		double velMag = velocity.magnitude();
		if( distance <= velMag * velMag / (2 * accel) ) {
			targetVelocity = velocity.normalize(Math.sqrt(2 * distance * accel));
			velocity = velocity.setToTarget(targetVelocity, accel * timeSeconds);
		}

		PhysicsVector avgVelocity = prevVelocity.add(velocity).multiply(.5);

		double upper = prevVelocity.magnitude() * timeSeconds + accel * timeSeconds * timeSeconds;
		double lower = prevVelocity.magnitude() * timeSeconds - accel * timeSeconds * timeSeconds;
		if(distance <= upper && distance >= lower) {
			position = targetPosition.peek().copy();
			velocity = new PhysicsVector(0,0);
			targetVelocity = new PhysicsVector(0,0);
		} else {
			position = position.add(avgVelocity.multiply(timeSeconds));
		}

		
	}

	
	@Override
	public boolean canMove(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTargetLocation(Point newTarget) {
		if (newTarget.x < 0 || newTarget.y < 0)
			return false;
		targetPosition.push(new PhysicsVector(newTarget));
		return true;
	}

}
