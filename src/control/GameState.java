package control;

import java.util.ArrayList;

import controller.GameBoard;


public class GameState {
	private ArrayList<Player> players;
	private GameBoard map;
	
	public void update(ArrayList<Player> players, GameBoard map) {
		this.players = players;
		this.map = map;
	}
	
	
	@Override
	public String toString() {
		
		return "";
	}

	public String getPlayerSelectables(int id) {
		Player temp = players.get(id-1);
		return temp.getGameObjects().toString();//Need to change to gameobject
	}

	

}
