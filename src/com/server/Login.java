package com.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
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
	// FINDBUG - initialized variable.
	Controller controller = null;
	int width;
	// FINDBUG - initialized variable.
	GameModel model = null;
	//File users = new File(System.getProperty("user.dir") + "/4x/users.txt");
	//File users = new File(System.getProperty("user.dir") + "/4x/users.txt");

	public Login() {
		accounts = new HashMap<String, String>();
		//loadObjects();
	}

	/**
	 * loadObjects() : This method is called from the constructor and reads the
	 * account data from users.txt
	 * 
	 * @return a list of the list of current Game Players
	 */

//	private void loadObjects() {
//		System.out.println("in loadObjects");
//		FileReader fi;
//		BufferedReader bi;
//
//		try {
//			fi = new FileReader(users);
//			bi = new BufferedReader(fi);
//
//			String temp;
//
//			while ((temp = bi.readLine()) != null) {
//
//				String[] split = temp.split(",");
//
//				String user = split[0];
//				String pw = split[1];
//				System.out.println("username: " + user);
//				System.out.println("password: "+ pw);
//				accounts.put(user, pw);
//
//			}
//
//			bi.close();
//			fi.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * saveObjects(): This method is called whenever a change to the HashMap
	 * occurs, which will output the account data to an external txt file
	 * users.txt
	 * 
	 * @return none
	 */

//	private void saveObjects() {
//		FileWriter fw;
//		BufferedWriter bw;
//
//		try {
//
//			fw = new FileWriter(users);
//			bw = new BufferedWriter(fw);
//
//			Object[] v = accounts.values().toArray();
//			Object[] k = accounts.keySet().toArray();
//
//			for (int x = 0; x < v.length; x++) {
//				bw.write(k[x] + "," + v[x] + "\n");
//			}
//
//			bw.close();
//			fw.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

	/**
	 * addUserToHashMap() : Adds the specified user and password to the accounts
	 * HashMap
	 * 
	 * @return a list of the list of current Game Players
	 */

//	public boolean addUserToHashMap(String user, String pass) {
//		if (accounts.containsKey(user))
//			return false;
//		else {
//			accounts.put(user, pass);
//			saveObjects();
//			return true;
//		}
//	}

	/**
	 * removeUserFromHashMap(): deletes a user from the accounts hashmap
	 * 
	 * @returns true when the User was successfully removed from the hashmap
	 */

//	public boolean removeUserFromHashMap(String user) {
//		if (!accounts.containsKey(user)) {
//			return false;
//		} else {
//			accounts.remove(user);
//			saveObjects();
//		}
//		return false;
//	}

	/**
	 * addUserToGame(): This method will check if the user entered the correct
	 * password, and will add the user to the game if it has a valid account
	 * 
	 * @return true if the player was added to the game successfully
	 */

	public boolean addUserToGame(String user, String pass) {
		// validate user
		if (accounts.containsKey(user) && pass.equals(accounts.get(user))) {
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
	 * hasUserInHashMap()
	 * 
	 * @return true if the given user name is in the accounts map
	 */

	public boolean hasUserInHashMap(String user) {
		return accounts.containsKey(user);
	}

	public GameModel getGameModel() {
		return model;
	}

	public Controller getController() {
		return controller;
	}
	
	public HashMap<String, String> getAccounts() {
		return accounts;
	}

}
