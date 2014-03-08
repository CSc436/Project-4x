package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import control.Actions;
import control.Command;
import control.Controller;
import control.GameState;
import control.PlayerCommands;
import control.Targets;

public class GameBoardTest {

	@Test
	public void testBoard() {
		PlayerCommands pc = new PlayerCommands();
		GameState gs = new GameState();
		ArrayList<Object> c = new ArrayList<Object>();
		Thread thread = new Thread(new Controller(pc, gs), "New Thread");
		thread.start();


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
		
		//Controller cont = new Controller(pc, gs);


		// System.out.println("Asshole");

	}
}
