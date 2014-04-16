package entities;

import java.util.Queue;

import control.UnitType;
import entities.units.Unit;

public interface Producer extends Simulatable {
	
	public void queueProduction( UnitType type );
	
	public Queue<Unit> getProducedUnits();
	
}
