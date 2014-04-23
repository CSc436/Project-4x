package entities.units.behavior;

import control.GameModel;
import entities.GameObject;
import entities.units.Unit;

public class NoAttack extends AttackBehavior {


	@Override
	public void attack(Unit currentUnit, int timeStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canAttack(GameModel model, Unit currentUnit) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAttacking() {
		// TODO Auto-generated method stub
		return false;
	}

}
