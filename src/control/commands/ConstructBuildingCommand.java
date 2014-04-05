package control.commands;

import control.Factory;
import control.GameModel;
import control.Player;
import entities.buildings.Building;
import entities.buildings.BuildingType;
import entities.gameboard.GameBoard;

/**
 * Programmer : Ben Deininger
 * 
 * Purpose : This class implements the Command class and builds a building at a
 * specified location on the GameBoard. The command requires the arguments in
 * the constructor, and will then call the factory to create and place the unit
 * on board, after first checking if it is possible.
 * 
 */
public class ConstructBuildingCommand implements Command {

	private int playerId;
	private BuildingType buildingType;
	private int xco;
	private int yco;
	private Player p;
	GameBoard gb;

	public ConstructBuildingCommand(Player p, int playerId, BuildingType bt,
			int xco, int yco, GameBoard gb) {

		this.p = p;
		this.playerId = playerId;
		this.buildingType = bt;
		this.xco = xco;
		this.yco = yco;
		this.gb = gb;

	}

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performCommand(GameModel model) {

		Building b = Factory.buildBuilding(p, playerId, buildingType, xco, yco,
				gb);

		if (b == null)
			return false;
		else
			return true;

	}
}
