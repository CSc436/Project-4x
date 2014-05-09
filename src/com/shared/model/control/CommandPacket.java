package com.shared.model.control;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import com.client.gameinterface.Console;
import com.shared.model.commands.Command;
import com.shared.model.commands.SendMessageCommand;

/**
 * 
 * @author Sean
 *
 * CommandPacket: Contains a set of commands to execute on the next turn,
 * along with how much time to simulate for.
 */
public class CommandPacket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4375055105232449139L;
	private int time;
	private int turnNumber;
	private Queue<Command> commandQueue = new LinkedList<Command>();

	public CommandPacket( int time, int turnNumber, Queue<Command> qc ) {
		this.setTime(time);
		this.setTurnNumber(turnNumber);
		this.setCommandQueue(qc);
	}
	
	public CommandPacket() {}
	
	public void executeOn( GameModel m ) {
		for( Command c : commandQueue ) {
			if(c.validateCommand(m))
				c.performCommand(m);
			else
				commandQueue.remove(c);
		}
	}
	
	public void addCommand( Command c ) {
		commandQueue.add(c);
	}
	
	public void addCommands( Queue<Command> qc ) {
		commandQueue.addAll(qc);
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Queue<Command> getCommandQueue() {
		return commandQueue;
	}

	public void setCommandQueue(Queue<Command> commandQueue) {
		this.commandQueue = commandQueue;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}
	
}
