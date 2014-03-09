package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import control.Actions;
import control.BuildingType;
import control.Command;
import control.Controller;
import control.GameState;
import control.PlayerCommands;
import control.Targets;
import control.UnitType;
import entities.buildings.Building;
import entities.gameboard.GameBoard;
import entities.util.Point;

public class GameBoardTest {

	@Test
	public void testBoard() {
		PlayerCommands pc = new PlayerCommands();
		GameState gs = new GameState();
		ArrayList<Object> c = new ArrayList<Object>();
		Controller cont = new Controller(pc, gs);
		Thread t = new Thread(cont);

		// cont.run();

		c.add("PLAYER X");
		c.add(0);
		pc.push(new Command(Actions.STARTUP_CREATE, Targets.PLAYER, c));

		c = new ArrayList<Object>();

		c.add("PLAYER Z");
		c.add(1);
		pc.push(new Command(Actions.STARTUP_CREATE, Targets.PLAYER, c));

		c = new ArrayList<Object>();

		c.add(200);
		c.add(200);
		pc.push(new Command(Actions.STARTUP_CREATE, Targets.MAP, c));
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		c = new ArrayList<Object>();
		c.add(0);
		c.add(BuildingType.BARRACKS);
		c.add(new Point(new Float(1.0), new Float(1.0)));
		pc.push(new Command(Actions.CREATE_BUILDING, Targets.BUILDING, c));

		/*
		 * c = new ArrayList<Object>(); c.add(UnitType.INFANTRY);
		 * 
		 * pc.push(new Command(Actions.CREATE, Targets.UNIT, c));
		 */
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// ---------------- assertions
		
		GameBoard game = cont.getGameBoard();
		assertEquals(200,game.getCols());
		assertEquals(200,game.getRows());
		//assertTrue(game.getTileAt(1, 1).isOccupiedByBuilding());
		assertTrue(game.getTileAt(1, 1).isOccupiedByBuilding());

		
		Building ts = gs.getPlayers().get(0).getGameObjects().getBuildings();
		
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		c = new ArrayList<Object>();
		c.add(0);
		c.add(ts);
		pc.push(new Command(Actions.SELECT, Targets.BUILDING, c));
		
		
		
		
		
		
		
		
		

		try {

			t.join();
		} catch (InterruptedException e) {
			System.out.println("EXITING");
			e.printStackTrace();
		}
	}
}
