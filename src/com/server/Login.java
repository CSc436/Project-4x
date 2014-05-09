package com.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.shared.model.control.GameModel;

/**
 * Login : This class establishes a login system for the Game. RPC calls will
 * have to be used by the clients to validate with the server
 * 
 * 
 * @author bdeining
 * 
 */

public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7009566566451237218L;
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

	/**
	 * loadObjects() : This method is called from the constructor and reads the
	 * account data from users.txt
	 * 
	 * @return a list of the list of current Game Players
	 */

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

	/**
	 * saveObjects(): This method is called whenever a change to the HashMap
	 * occurs, which will output the account data to an external txt file
	 * users.txt
	 * 
	 * @return none
	 */

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

	/**
	 * addUserToHashMap() : Adds the specified user and password to the accounts
	 * HashMap
	 * 
	 * @return a list of the list of current Game Players
	 */

	public boolean addUserToHashMap(String user, String pass) {
		if (accounts.containsKey(user))
			return false;
		else {
			accounts.put(user, pass);
			saveObjects();
			return true;
		}
	}

	/**
	 * removeUserFromHashMap(): deletes a user from the accounts hashmap
	 * 
	 * @returns true when the User was successfully removed from the hashmap
	 */

	public boolean removeUserFromHashMap(String user) {
		if (!accounts.containsKey(user)) {
			return false;
		} else {
			accounts.remove(user);
			saveObjects();
		}
		return false;
	}

	/**
	 * addUserToGame(): This method will check if the user entered the correct
	 * password, and will add the user to the game if it has a valid account
	 * 
	 * @return true if the player was added to the game successfully
	 */

	public boolean addUserToGame(String user, String pass) {
		// validate user
		if (accounts.containsKey(user) && pass.equals(accounts.get(user))
				&& !players.contains(user)) {
			players.add(user);
			return true;
		} else
			return false;
	}

	/**
	 * removeUserFromGame()
	 * 
	 * @returns true when the user was successfully removed from the game list
	 */

	public boolean removeUserFromGame(String user) {

		if (players.contains(user)) {
			players.remove(user);
			return true;
		} else
			return false;
	}

	/**
	 * createGame() : This method should be called when all the players are
	 * added to the game and the dimensions are set.
	 * 
	 * @return true if the game has at least 2 players and the board has at
	 *         least 1 tile. (the game was created successfully).
	 */

	public boolean createGame() {

		if (players.size() > 1 && width > 0) {
			model = new GameModel();
			controller = new Controller(model);
			return true;
		} else
			return false;
	}

	/**
	 * setWidth() : Sets the width of the board for this Game Session, which is
	 * then sent to the GameBoard when instantiated
	 * 
	 * 
	 * @return none
	 */

	public void setWidth(int w) {
		width = w;
	}

	/**
	 * getGamePlayers()
	 * 
	 * @return true if the given user name is in the accounts map
	 */

	public boolean hasUserInHashMap(String user) {
		return accounts.containsKey(user);
	}

	/**
	 * getGamePlayers()
	 * 
	 * @return true if the player list is not empty
	 */

	public boolean hasPlayers() {
		return !players.isEmpty();
	}

	/**
	 * getGamePlayers()
	 * 
	 * @return a list of the list of current Game Players
	 */

	public ArrayList<String> getGamePlayers() {
		return players;
	}

	public GameModel getGameModel() {
		return model;
	}

	public Controller getController() {
		return controller;
	}

}
