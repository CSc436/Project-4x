package entities.units.behavior;

import control.GameModel;
import entities.util.Point;

public class NoMove extends MoveBehavior{


	@Override
	public void move(int timeScale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canMove(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTargetLocation(Point newTarget) {
		// TODO Auto-generated method stub
		return false;
	}

}
