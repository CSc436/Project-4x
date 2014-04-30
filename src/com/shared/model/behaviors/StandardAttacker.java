package com.shared.model.behaviors;

import com.shared.utils.PhysicsVector;



public class StandardAttacker implements Attacker {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7599376887116225985L;
	private int coolDown; // Number of milliseconds for attack cooldown
	private int coolDownTimer = 0; // Number of milliseconds since previous attack
	private Combatable target = null; // Current attack target
	private float range; // Attack range
	private int strength; // Attack strength
	private boolean isAttacking; // Whether or not I am actively attacking my target
	/*
	 * Must have an associated movementBehavior in order to calculate distance from
	 * target and in order to perform appropriate attack-move commands
	 */
	private Movable moveBehavior;
	
	public StandardAttacker() {
		this.coolDown = 1000;
		this.range = 1.0F;
		this.strength = 1;
		this.isAttacking = false;
	}
	
	public StandardAttacker(int strength, float range, float coolDown, Movable mb) {
		this.coolDown = 1000;
		this.range = range;
		this.strength = 1;
		this.isAttacking = false;
		this.moveBehavior = mb;
	}
	
	public void setTarget( Combatable target ) {
		this.target = target;
	}
	
	public void startAttack() {
		isAttacking = true;
	}
	
	public void stopAttack() {
		isAttacking = false;
	}

	@Override
	public void simulateAttack(int timeStep) {
		if(isAttacking) {
			PhysicsVector targetPosition = target.getPosition();
			PhysicsVector myPosition = moveBehavior.getPosition();
			double distanceToTarget = targetPosition.sub(myPosition).magnitude();
			
			coolDownTimer += timeStep;
			int numAttacks = coolDownTimer / coolDown;
			coolDownTimer %= coolDown;
			
			if( distanceToTarget <= range ) {
				target.takeDamage(numAttacks * strength);
			} else {
				double x = targetPosition.getX();
				double y = targetPosition.getY();
				moveBehavior.setMoveTarget(x, y);
			}
		} else {
			coolDownTimer = coolDown;
		}
	}
	
}
