package entities.units;

import java.util.HashMap;

import control.Player;


//queueing of commands for controller, controller checks queue each
//time segment, decides how many commands to execute. New commands
//added to end of queue

public class General extends Agent{

	private int range;
	private AIControl brain;
	public General(Player p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setActions() {
		allActions = new HashMap<String, String>();
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String decision() {
		
	}
	
	
	//add unit to collection
	
	//make decision command
	
	//what options each general has:
	//rally troops, defensive position, attack, move, remove troops
	//add troops
	
}
