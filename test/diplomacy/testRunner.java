package diplomacy;

import static org.junit.Assert.*;

import org.junit.Test;

import entities.diplomacy.DiplomacyStandingEnum;
import entities.diplomacy.IDiplomacy;
import entities.diplomacy.types.FreeForAllDiplomacy;
import entities.diplomacy.types.UnlockedDiplomacy;

public class testRunner {

	/**
	 * This test tests whether the FreeForAll dipomacy works. It also tests
	 * whether alliances are locked when free for all is set.
	 */
	@Test
	public void testFreeForAll() {
		IDiplomacy d = new FreeForAllDiplomacy(4);
		/** Ensure that everyone is initially set to Enemy */
		assertEquals(DiplomacyStandingEnum.Enemy, d.getStanding(0, 1));

		/** Ensure Alliances are locked */
		d.setMutual(0, 1, DiplomacyStandingEnum.Allied);
		assertEquals(DiplomacyStandingEnum.Enemy, d.getStanding(0, 1));
		assertEquals(DiplomacyStandingEnum.Enemy, d.getStanding(1, 0));
	}

	/**
	 * Tests Unlocked Alliances.
	 */
	@Test
	public void testUnlockedAlliances() {
		IDiplomacy d = new UnlockedDiplomacy(4, DiplomacyStandingEnum.Enemy);
		
		/** Ensures that initial setting of alliances works */
		assertEquals(DiplomacyStandingEnum.Enemy, d.getStanding(0, 1));

		/** Set Mutual sets both player's alliances towards each other */
		d.setMutual(0, 1, DiplomacyStandingEnum.Allied);
		assertEquals(DiplomacyStandingEnum.Allied, d.getStanding(0, 1));
		assertEquals(DiplomacyStandingEnum.Allied, d.getStanding(1, 0));

		/** Set Standing only modifies one player's alliances towards the other */
		d.setStanding(0, 1, DiplomacyStandingEnum.Neutral);
		assertEquals(DiplomacyStandingEnum.Neutral, d.getStanding(0, 1));
		assertEquals(DiplomacyStandingEnum.Allied, d.getStanding(1, 0));
	}
}
