package com.server;

import java.util.Vector;

import control.Player;

public class Dispatch implements ModelEventListener {
	
	//TODO: Change this to thread-safe vectors.
	private Player[] players;
	
	public Dispatch(Player[] players) {
		this.players = players;
	}
	
	


	@Override
	public void onModelEvent(ModelEvent me) {
		// TODO Auto-generated method stub
		
	}
}