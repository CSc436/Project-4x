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
		coolDownTimer += timeStep;
		int numAttacks = coolDownTimer / coolDown;
		coolDownTimer %= coolDown;
		target.getStats().health -= (numAttacks * currentUnit.getStats().damage); 
	}

	@Override
	public boolean canAttack(GameModel model, Unit currentUnit) {
		PhysicsVector targetPosition = target.getPosition();
		PhysicsVector myPosition = currentUnit.getUnitPosition();
		double distanceToTarget = myPosition.sub(targetPosition).magnitude();
		if( distanceToTarget <= currentUnit.getStats().range ) {
			return true;
		}
		else
			return false;
	}

	@Override
	public void setAttackTarget(GameObject newTarget) {
		target = newTarget;
	}

	@Override
	public void resetAttackTarget() {
		target = null;
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

	@Override
	public boolean isAttacking() {
		if (target != null)
			return true;
		else
			return false;
	}

}
