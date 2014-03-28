package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import control.BuildingType;
import control.Controller;
import control.GameState;
import control.PlayerCommands;
import control.UnitType;
import control.commands.Command;
import deprecated.Actions;
import deprecated.Targets;
import entities.util.Point;

public class GameBoardTest {

	@Test
	public void testBoard() {
		PlayerCommands pc = new PlayerCommands();
		GameState gs = new GameState();
		ArrayList<Object> c = new ArrayList<Object>();
		Controller cont = new Controller();
		Thread t = new Thread(cont);

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		try {

			t.join();
		} catch (InterruptedException e) {
			System.out.println("EXITING");
			e.printStackTrace();
		}
	}
}
