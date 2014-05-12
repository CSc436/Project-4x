package com.shared.model.behaviors;

import java.io.Serializable;

public interface Attacker extends Serializable {
	
	public void simulateAttack( int timeStep );
	
	public void setTarget( Combatable target );
	
	public void startAttack();
	
	public void stopAttack();
	
	public boolean isAnimatingAttack();
	
}
