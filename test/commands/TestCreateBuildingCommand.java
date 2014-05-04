package commands;


import org.junit.Test;
import com.server.Controller;
import com.shared.model.commands.AddPlayerCommand;
import com.shared.model.commands.Command;

import com.shared.model.control.GameModel;

public class TestCreateBuildingCommand {

	@Test
	public void testCreate() {

		GameModel model = new GameModel();
		Controller controller = new Controller(model);

		Command comm = new AddPlayerCommand("meathook");

		Thread t = new Thread(controller);
		t.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
