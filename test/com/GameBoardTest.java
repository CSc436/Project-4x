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
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		c = new ArrayList<Object>();
		c.add(0);
		c.add(BuildingType.BARRACKS);
		c.add(new Point(1, 1));
		pc.push(new Command(Actions.CREATE_BUILDING, Targets.BUILDING, c));

		/*
		 * c = new ArrayList<Object>(); c.add(UnitType.INFANTRY);
		 * 
		 * pc.push(new Command(Actions.CREATE, Targets.UNIT, c));
		 */

		try {

			t.join();
		} catch (InterruptedException e) {
			System.out.println("EXITING");
			e.printStackTrace();
		}
	}
}
