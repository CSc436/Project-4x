package control;

import java.util.Scanner;

public class PlayerTextInput implements Runnable {
	private PlayerCommands instructions;
	public PlayerTextInput(PlayerCommands instructions) {
		this.instructions = instructions;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		int result=0;
		Scanner keyboard = new Scanner(System.in);
		while(result != 3) {
			System.out.println("1. create Unit\n2. build building\n3. quit");
			result = keyboard.nextInt();
			instructions.push(result);
		}
		keyboard.close();
		System.out.println("text input thread ended");
	}

}