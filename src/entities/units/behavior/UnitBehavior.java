package entities.units.behavior;

import control.GameModel;
import entities.units.Unit;
import entities.util.Point;

public abstract class UnitBehavior {
	protected Unit currentUnit;
	protected AttackBehavior attackBehavior;
	protected MoveBehavior movementBehavior;
	
	
	public abstract boolean performBehavior(GameModel model, Unit currUnit, int timeScale);
	
	public abstract void setTargetLocation(GameModel model, Point target);

}
