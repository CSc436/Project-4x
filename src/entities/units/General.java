package entities.units;

import control.AIControl;
import control.Player;

//queueing of commands for controller, controller checks queue each
//time segment, decides how many commands to execute. New commands
//added to end of queue

public class General extends Agent {

	private int range;
	private AIControl brain;

	public General(Player p, int idno) {
		super(p, idno);
		// TODO Auto-generated constructor stub
	}

	public String decision() {
		return "NO";
	}

	// add unit to collection

	// make decision command

	// what options each general has:
	// rally troops, defensive position, attack, move, remove troops
	// add troops

}
