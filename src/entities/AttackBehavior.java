package entities;

import java.io.Serializable;

import com.shared.MoveBehavior;

public class AttackBehavior implements ObjectBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7599376887116225985L;
	private int coolDown; // Number of milliseconds for attack cooldown
	private int coolDownTimer = 0; // Number of milliseconds since previous attack
	private GameObject target = null; // Current attack target
	private double range; // Attack range
	private int strength; // Attack strength
	private boolean isAttacking; // Whether or not I am actively attacking my target
	/*
	 * Must have an associated movementBehavior in order to calculate distance from
	 * target and in order to perform appropriate attack-move commands
	 */
	private MoveBehavior moveBehavior;
	
	public AttackBehavior() {
		this.coolDown = 1000;
		this.range = 1.0;
		this.strength = 1;
		this.isAttacking = false;
	}
	
	public AttackBehavior(int coolDown, double range, double strength) {
		this.coolDown = 1000;
		this.range = 1.0;
		this.strength = 1;
		this.isAttacking = false;
	}
	
	public void setTarget( GameObject target ) {
		this.target = target;
	}
	
	public void startAttack() {
		isAttacking = true;
	}
	
	public void stopAttack() {
		isAttacking = false;
	}

	@Override
	public void simulateTimeStep(int timeStep) {
		if(moveBehavior.inRange())
		// TODO Auto-generated method stub
		coolDownTimer += timeStep;
		int numAttacks = coolDownTimer / coolDown;
		coolDownTimer %= coolDown;
		
		target.getHealthBehavior().takeDamage(numAttacks * strength);
	}
	
	
	
	
}
