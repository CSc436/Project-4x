package entities;

import java.util.HashMap;

public abstract class GameObject {
	
	protected HashMap<String, String> allActions;
	public GameObject() {
		setActions();
	}
	protected abstract void setActions();
	
	public abstract HashMap<String, String> getActions();
}