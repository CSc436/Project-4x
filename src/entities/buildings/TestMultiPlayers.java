package entities.buildings;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import control.Factory;
import control.Player;
import entities.GameObjectType;
import entities.gameboard.GameBoard;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class TestMultiPlayers {

	@Test
	public void mtp(){
		Player p = new Player ("Xu",101);
		Player q = new Player ("Graham", 202);
		GameBoard g = new GameBoard(50, 50);
		Factory f = new Factory();
		Bank bk = (Bank) f.buildBuilding(p, 101, BuildingType.BANK, 0, 0, g);
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
