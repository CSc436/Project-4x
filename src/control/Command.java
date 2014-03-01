package control;

import java.util.ArrayList;

public class Command {
	private final Actions action;
	private final Targets target;
	private final ArrayList<Object> payload;
	public Command(Actions action, Targets target, ArrayList<Object> payload) {
		this.action = action;
		this.target = target;
		this.payload = new ArrayList<Object>(payload);
	}
	
	public boolean validateCommand() {
		return true;
	}
	
	public Actions getAction() {
		return action;
	}
	
	public Targets getTarget() {
		return target;
	}
	
	public ArrayList<Object> getPayload() {
		return payload;
	}
}
