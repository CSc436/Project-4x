package entities;

public interface Locatable {
	//This interface is used to determine when a player needs to know about something.
	public float getX();
	public float getY();
	
	public int getOwner();
}
