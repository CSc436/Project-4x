package com.server;

import control.Player;
import entities.units.Agent;

public class Dispatch implements ModelEventListener {
	
	//TODO: Change this to thread-safe vectors.
	private Player[] players;
	
	public Dispatch(Player[] players) {
		this.players = players;
	}
	
	


	@Override
	public void onModelEvent(ModelEvent me) {
		// TODO Auto-generated method stub
		switch (me.type) {
		case UNIT:
			
			Agent a = (Agent)me.getPayload();
			
			//Now add a to hash maps.
			
			//Now determine who should be notified about A.
			
			//Now add a to the appropriate notification queues.
			
			//TODO: Implement these things.
			
			break;
		case BUILDING:
			
			break;
			
		default:
			throw new IllegalArgumentException("A valid Target was not specified.");
		}
		
		
		
	}
}