package com.shared.model.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Login {

	private HashMap<String, String> accounts;
	private ArrayList<String> players;
	Controller controller;
	int width;
	GameModel model;
	File users = new File(System.getProperty("user.dir") + "/src/users.txt");

	public Login() {
		accounts = new HashMap<String, String>();
		players = new ArrayList<String>();
		loadObjects();

	}

	private void loadObjects() {
		FileReader fi;
		BufferedReader bi;

		try {
			fi = new FileReader(users);
			bi = new BufferedReader(fi);

			String temp;

			while ((temp = bi.readLine()) != null) {

				String[] split = temp.split(",");

				String user = split[0];
				String pw = split[1];
				accounts.put(user, pw);

			}

			bi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveObjects() {
		FileWriter fw;
		BufferedWriter bw;

		try {

			fw = new FileWriter(users);
			bw = new BufferedWriter(fw);

			Object[] v = accounts.values().toArray();
			Object[] k = accounts.keySet().toArray();

			for (int x = 0; x < v.length; x++) {
				bw.write(k[x] + "," + v[x] + "\n");
			}

			bw.close();
			fw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean addUserToHashMap(String user, String pass) {
		if (accounts.containsKey(user))
			return false;
		else {
			accounts.put(user, pass);
			saveObjects();
			return true;
		}
	}

	public boolean removeUserFromHashMap(String user) {
		if (!accounts.containsKey(user)) {
			return false;
		} else {
			accounts.remove(user);
			saveObjects();
		}
		return false;
	}

	public boolean addUserToGame(String user, String pass) {
		// validate user
		if (accounts.containsKey(user) && pass.equals(accounts.get(user))
				&& !players.contains(user)) {
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
