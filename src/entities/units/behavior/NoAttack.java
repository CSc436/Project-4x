package entities.units.behavior;

import control.GameModel;

public class NoAttack extends AttackBehavior {

	@Override
	public boolean attack(GameModel model) {
		return true;
	}

}
