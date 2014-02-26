package entities.buildings;

import static org.junit.Assert.*;

import org.junit.Test;

import control.Player;

public class TestBuildings {

	@Test
	public void testBrk() {
		// TODO Auto-generated method stub
			Player p = new Player("Xu");
			Barracks brk = new Barracks(p, 20, 20, 100, 1);
			assertEquals(brk.getSoldiers(),70);
			brk.setSoldiers(brk.getLevel());
			assertEquals(brk.getSoldiers(),120);
			brk.upgrade();assertEquals(brk.getLevel(),2);
			assertEquals(brk.getSoldiers(),200);
			brk.setSoldiers(brk.getLevel());
			assertEquals(brk.getSoldiers(),280);
			brk.upgrade();assertEquals(brk.getLevel(),3);
			brk.upgrade();assertEquals(brk.getLevel(),4);
			brk.upgrade();assertEquals(brk.getLevel(),5);
			assertEquals(brk.getSoldiers(),480);
			brk.upgrade();assertEquals(brk.getLevel(),5);assertEquals(brk.getSoldiers(),680);
			brk.upgrade();assertEquals(brk.getLevel(),5);assertEquals(brk.getSoldiers(),880);
			brk.upgrade();assertEquals(brk.getLevel(),5);assertEquals(brk.getSoldiers(),1080);
	}

}
