package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import control.BuildingType;
import control.Controller;
import control.Player;
import control.Factory;
import control.UnitType;
import entities.Action;
import entities.PerfectCivilization;
import entities.PlayerUnits;
import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.gameboard.GameBoard;
import entities.units.Agent;
import entities.units.Unit;
import entities.units.pawns.Infantry;

public class GameBoardTest {

	@Test
	public void testBoard() {

		GameBoard game = new GameBoard(20, 20);
		assertEquals(20, game.getRows());
		assertEquals(20, game.getCols());

	}
}
