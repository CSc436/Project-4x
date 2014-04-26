package entities.behaviors;

import java.io.Serializable;
import java.util.Queue;

import control.UnitType;
import entities.units.Unit;

public interface Producer extends Serializable {
	
	public void simulateProduction( int timeStep );
	
	public void queueProduction( UnitType type );
	
	public Queue<UnitType> getProducedUnits();
	
}
