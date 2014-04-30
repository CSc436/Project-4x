package com.shared.model.behaviors;

import java.io.Serializable;
import java.util.Queue;

import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public interface Producer extends Serializable {
	
	public void simulateProduction( int timeStep );
	
	public void queueProduction( UnitType type );
	
	public Queue<UnitType> getProducedUnits();
	
}
