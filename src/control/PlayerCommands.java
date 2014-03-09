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
		//System.out.println("Migrate to Ben Whitely's hashing thing.");
		//TODO: Migrate to Ben Whitely's hashing thing.
	}
	
	public synchronized Command pop() {
		Command x = commands.poll();
		
		System.out.println(x.toString());
		return x;
	}
	
	public synchronized Queue<Command> dump() {
		//System.out.println("Migrate to Ben Whitely's hashing thing.");
		//TODO: Migrate to Ben Whitely's hashing thing.
		
		System.out.println("Dumping commands: " + commands.size());
		Queue<Command> temp = new ArrayDeque<Command>();
		temp.addAll(commands);
		commands.clear();
		return temp;
	}
	
}