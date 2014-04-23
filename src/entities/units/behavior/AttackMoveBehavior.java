package entities.units.behavior;

import control.GameModel;
import entities.units.Unit;
import entities.util.Point;

/**
 * Starts with normal attack and normal move behavior
 * 
 * Attack when possible (target in range)
 * 
 * @author Lloyd
 *
 */
public class AttackMoveBehavior extends UnitBehavior{
	NormalMovement movementBehavior;
	
	//User wishes for unit to attack/move to a location
	public AttackMoveBehavior(Unit currUnit, Point targetLocation) {
		attackBehavior = new NormalAttack();
		movementBehavior = new NormalMovement(currUnit, targetLocation);
	}

	@Override
	public boolean performBehavior(GameModel model, Unit currUnit, int timeScale) {
		//TODO: needs Ben's line of sight stuff
		if (attackBehavior.isAttacking())
			attackBehavior.attack(currUnit, timeScale);
		else if (attackBehavior.enemyInSight(model, currentUnit)) {
			if (attackBehavior.canAttack(model, currentUnit)) {
				attackBehavior.attack(currentUnit, timeScale);
			}
			else
			{
				
			}
		}
		movementBehavior.move(timeScale);
		return true;
	}

	@Override
	public boolean setTargetLocation(Point target) {
		return movementBehavior.setTargetLocation(target);
	}
	
	

}
