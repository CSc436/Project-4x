package entities;

import control.Player;

public interface Locatable {
	//This interface is used to determine when a player needs to know about something.
	public int getX();
	public int getY();
	
	public Player getOwner();
}
