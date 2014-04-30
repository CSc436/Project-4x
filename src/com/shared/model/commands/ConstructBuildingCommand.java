package com.shared.model.commands;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.control.Factory;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;


public class ConstructBuildingCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1798034723745667705L;
	private int playerId;
	private BuildingType buildingType;
	private int xco;
	private int yco;
	private Player p;
	GameBoard gb;

	public ConstructBuildingCommand(Player p, int playerId, BuildingType bt, int xco,
			int yco, GameBoard gb) {

		this.p = p;
		this.playerId = playerId;
		this.buildingType = bt;
		this.xco = xco;
		this.yco = yco;
		this.gb = gb;

	}
	
	public ConstructBuildingCommand() {}

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
