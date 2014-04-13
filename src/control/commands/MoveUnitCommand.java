package control.commands;

import control.GameModel;

public class MoveUnitCommand implements Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3924368375602014687L;
	private int entityID;
	private double x;
	private double y;
	
	public MoveUnitCommand() {}
	
	public MoveUnitCommand( int entityID, double x, double y ) {
		this.entityID = entityID;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performCommand(GameModel model) {
		model.getGameObject(entityID).getMoveBehavior().setTarget(x, y);
		return false;
	}

}
