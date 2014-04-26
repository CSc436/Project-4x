package com;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.shared.model.control.Login;

public class TestLogin {

	@Test
	public void testMap() {

		// persistent testing
		// static users :
		// Meathook,43
		// Satan,4321
		// God,1234

		Login log = new Login();
		assertFalse(log.hasPlayers());
		assertTrue("God", log.addUserToGame("God", "1234"));

		assertFalse(log.addUserToHashMap("God", "1234"));
		assertTrue(log.hasUserInHashMap("God"));

		assertFalse(log.addUserToGame("God", "21234"));
		assertFalse(log.addUserToGame("God", "1234"));

		assertTrue(log.addUserToGame("Satan", "4321"));

		assertFalse(log.addUserToGame("Satan", "4321"));

		ArrayList<String> players = log.getGamePlayers();
		assertEquals("God", players.get(0));
		assertEquals("Satan", players.get(1));

		assertFalse(log.addUserToHashMap("Meathook", "43"));

	}
}
