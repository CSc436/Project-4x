package com.client;

import static com.google.gwt.query.client.GQuery.$;

import com.client.gameinterface.Console;
import com.client.gameinterface.GameInterface;
import com.client.model.ClientController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.query.client.Function;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class PlayGame implements EntryPoint {

	public void onModuleLoad() {

		// Set actions on loading screen
		$("#loading-actions").html("INITIALIZING GAME");
		
		$("#login-button").click(new Function() {
			public boolean f(Event e) {
				return play();
			}
		});
		
		// Allow use to press enter to loggin, that way we can test slightly faster
		$("#login-username").keypress(new Function() {
			public boolean f (Event e)
			{
				if (e.getKeyCode() == 13) 
				{
					return play();
				}
				return false;
			}
		});
		// Allow use to press enter to loggin, that way we can test slightly faster
		$("#login-password").keypress(new Function() {
			public boolean f (Event e)
			{
				if (e.getKeyCode() == 13) 
				{
					return play();
				}
				return false;
			}
		});
		
	}

	private boolean play()
	{
		Console.log($("#login-username").val());
		Console.log($("#login-password").val());

		// Added quick hack to get loggin name to be chat name - Nick
		// Possibly move to run method? 
		GameInterface.setPlayerName($("#login-username").val());
		
		Window.alert("Login?!");
		// Remove login screen
		$("#login-screen").remove();
		
		// Init game in 1 second
		// (This is purely to see the loading screen)
		Timer t = new Timer() {
			@Override
			public void run() {
				// Set actions on loading screen
				$("#loading-actions").html("INITIALIZING MODEL");
				ClientController theModel = new ClientController();
				theModel.run();
				// Set actions on loading screen
				$("#loading-actions").html("SETTING CANVAS");
				GameCanvas canvas = new GameCanvas(theModel);
				GameInterface.init(theModel, canvas);
				// Set actions on loading screen
				$("#loading-actions").html("RETRIEVING GAME STATE");
				/** NOTE: loading screen now gets removed once the game model is set **/
				/** Which happens in ClientModel.java **/
			}
		};
		// Schedule the dummy timer
		t.schedule(1000);
		return true;
	}
}
