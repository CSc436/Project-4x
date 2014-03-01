package entities;

import java.util.HashMap;

import control.Tools;

public abstract class GameObject {
	private final long id = Tools.generateUniqueId();
	private final int playerId;
	protected HashMap<String, String> allActions;
	public GameObject(long playerId) {
		setActions();
		this.playerId = playerId;
		//allActions = new HashMap<String,String>();
	}
	protected abstract void setActions();
	
	public abstract HashMap<String, String> getActions();
	
	public long getId() {
		return id;
	}
	public int getPlayerId() {
		return playerId;
	}
}