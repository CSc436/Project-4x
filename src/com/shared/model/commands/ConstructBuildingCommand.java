package com.shared.model.commands;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.control.Factory;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;
import com.shared.utils.Coordinate;

public class ConstructBuildingCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1798034723745667705L;
	private int playerId;
	private BuildingType buildingType;
	private Coordinate c;

	public ConstructBuildingCommand(BuildingType bt, int playerId, Coordinate c) {

		this.playerId = playerId;
		this.buildingType = bt;
		this.c = c;

	}

	public ConstructBuildingCommand() {
	}

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {

		Player p = model.getPlayers().get(playerId);
		GameBoard gb = model.getBoard();

		Building b = Factory.buildBuilding(p, playerId, buildingType, c.fx(),
				c.fy(), gb);

		if (b == null) {
			return false;
		} else {
			model.getProducedBuildings().add(b);
			p.addBuilding(b);
			return true;
		}

	}
}
