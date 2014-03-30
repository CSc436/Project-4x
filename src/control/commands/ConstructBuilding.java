package control.commands;

import java.util.UUID;

import control.BuildingType;
import control.Factory;
import control.GameModel;
import control.Player;
import entities.buildings.Building;
import entities.gameboard.GameBoard;


public class ConstructBuilding implements Command {

	private int playerId;
	private BuildingType buildingType;
	private int xco;
	private int yco;
	private Player p;
	GameBoard gb;

	public ConstructBuilding(Player p, int playerId, BuildingType bt, int xco,
			int yco, GameBoard gb) {

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
