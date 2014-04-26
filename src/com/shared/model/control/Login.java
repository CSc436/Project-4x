package com.shared.model.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Login {

	private HashMap<String, String> accounts;
	private ArrayList<String> players;
	Controller controller;
	int width;
	GameModel model;
	File users = new File("users.txt");

	public Login() {
		accounts = new HashMap<String, String>();
		players = new ArrayList<String>();
		// model = new GameModel();
		// controller = new Controller();
		// model = controller.getGameModel();
		loadObjects();
	}

	private void loadObjects() {

		try {
			FileInputStream fi = new FileInputStream(users);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void saveObjects() {

	}

	public boolean addUserToHashMap(String user, String pass) {
		if (accounts.containsKey(user))
			return false;
		else {
			accounts.put(user, pass);
			return true;
		}
	}

	public boolean removeUserFromHashMap(String user) {
		if (!accounts.containsKey(user)) {
			return false;
		} else {
			accounts.remove(user);
		}
		return false;
	}

	public boolean addUserToGame(String user, String pass) {
		// validate user
		if (accounts.containsKey(user) && pass == accounts.get(user)) {
			players.add(user);
			return true;
		} else
			return false;
	}

	public boolean removeUserFromGame(String user) {

		if (players.contains(user)) {
			players.remove(user);
			return true;
		} else
			return false;
	}

	public boolean createGame() {

		if (players.size() > 1 && width > 0) {
			model = new GameModel(players, width);
			controller = new Controller(model);
			return true;
		} else
			return false;
	}

	public void setWidth(int w) {
		width = w;
	}

	public boolean hasUserInHashMap(String user) {
		return accounts.containsKey(user);
	}

	public boolean hasPlayers() {
		return !players.isEmpty();
	}

	public ArrayList<String> getGamePlayers() {
		return players;
	}

}
