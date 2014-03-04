package control;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlayerCommands {
	
	private Queue<Command> commands;
	
	public PlayerCommands() {
		commands = new ArrayDeque<Command>();
	}
	
	public synchronized void push(Command command) {
		commands.add(command);
		System.out.println("Migrate to Ben Whitely's hashing thing.");
		//TODO: Migrate to Ben Whitely's hashing thing.
	}
	
	public synchronized Command pop() {
		return commands.poll();
	}
	
	public synchronized Queue<Command> dump() {
		Queue<Command> temp = new ArrayDeque<Command>(commands);
		commands.clear();
		return temp;
	}
	
}