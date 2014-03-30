package entities.units.agents;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import entities.stats.UnitStats;

public class TestPrestige {

	@Test
	public void testgp(){
		General gr = new General(new UUID(0, 0),111, 0, 0);
		gr.prestige = 0;
		assertEquals(gr.acceptCommandByPrestige(gr.getPrestige()),"no");
		gr.addPrestige(100);
		assertEquals(gr.acceptCommandByPrestige(gr.getPrestige()),"maybe no");
		gr.addPrestige(1100);
		assertEquals(gr.acceptCommandByPrestige(gr.getPrestige()),"yes");
	}
}
