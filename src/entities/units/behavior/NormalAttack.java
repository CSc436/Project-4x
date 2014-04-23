package entities.units.behavior;

import java.util.ArrayList;

import com.shared.PhysicsVector;

import control.GameModel;
import entities.GameObject;
import entities.units.Unit;
import entities.util.Point;

public class NormalAttack extends AttackBehavior{

	@Override
	public void attack(Unit currentUnit, int timeStep) {
		
		
		Point targetPosition = target.getPosition();
		Point myPosition = currentUnit.getPosition();
		double distanceToTarget = currentUnit.distance(target);
		coolDownTimer += timeStep;
		int numAttacks = coolDownTimer / coolDown;
		coolDownTimer %= coolDown;

		if( distanceToTarget <= range ) {
			target.getHealthBehavior().takeDamage(numAttacks * strength);
		} else {
			double x = targetPosition.getX();
			double y = targetPosition.getY();
			moveBehavior.setMoveTarget(x, y);
		}
		
		
		
		currentUnit.attack(target);
	}

	@Override
	public boolean canAttack(GameModel model, Unit currentUnit) {
		if(currentUnit.getStats().range >= currentUnit.distance(target))
			return true;// TODO Auto-generated method stub
		else
			return false;
	}

	@Override
	public void setAttackTarget(GameObject newTarget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetAttackTarget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean enemyInSight(GameModel model, Unit currentUnit) {
		if (target != null)
			return true;
		ArrayList<GameObject> visible = model.getVisibleUnits(currentUnit);
		if (visible.size() == 0)
			return false;
		target = visible.get(0);
		return true;
	}

}
