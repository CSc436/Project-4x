package com;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.shared.model.control.Login;

public class TestLogin {

	@Test
	public void testMap() {

		// Non persistent

		Login log = new Login();
		assertFalse(log.hasPlayers());
		assertFalse("God", log.addUserToGame("God", "1234"));
		assertFalse(log.hasUserInHashMap("God"));

		assertTrue(log.addUserToHashMap("God", "1234"));
		assertFalse(log.addUserToHashMap("God", "1234"));
		assertTrue(log.hasUserInHashMap("God"));

		assertFalse(log.addUserToGame("God", "21234"));
		assertTrue(log.addUserToGame("God", "1234"));

		assertFalse(log.addUserToGame("Satan", "4321"));
		assertTrue(log.addUserToHashMap("Satan", "4321"));
		assertTrue(log.addUserToGame("Satan", "4321"));

		ArrayList<String> players = log.getGamePlayers();
		assertEquals("God", players.get(0));
		assertEquals("Satan", players.get(1));

	}
}
