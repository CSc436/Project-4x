package com.shared.model.buildings;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.shared.model.control.Factory;
import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;

public class TestMultiPlayers {

	@Test
	public void mtp(){
		Player p = new Player ("Xu",101);
		Player q = new Player ("Graham", 202);
		GameBoard g = new GameBoard(50, 50);
		Factory f = new Factory();
		Bank bk = (Bank) f.buildBuilding(p, 101, BuildingType.BANK, 0f, 0f, g);
		bk.gold = 1000000;
		p.resources.setGold(0);
		q.resources.setGold(0);
		bk.withdraw(1000);
		bk.withdraw(500);
		assertEquals(p.resources.getGold(),1000);
		assertEquals(q.resources.getGold(),500);
		assertEquals(bk.gold,998500);
	}
}
