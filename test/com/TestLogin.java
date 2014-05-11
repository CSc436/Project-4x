package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.server.Login;
import com.shared.model.control.Player;

public class TestLogin {

	@Test
	public void testMap() {

		// persistent testing
		// static users :
		// Meathook,43
		// Satan,4321
		// God,1234

		Login log = new Login();
		assertTrue("God", log.addUserToGame("God", "1234"));

		assertFalse(log.addUserToHashMap("God", "1234"));
		assertTrue(log.hasUserInHashMap("God"));

		assertFalse(log.addUserToGame("God", "21234"));
		assertFalse(log.addUserToGame("God", "1234"));

		assertTrue(log.addUserToGame("Satan", "4321"));

		assertFalse(log.addUserToGame("Satan", "4321"));

		Player[] players = (Player[])log.getGameModel().getPlayers().values().toArray();
		assertEquals("God", players[0]);
		assertEquals("Satan", players[1]);

		assertFalse(log.addUserToHashMap("Meathook", "43"));

		assertEquals(2, players.length);

	}
}
