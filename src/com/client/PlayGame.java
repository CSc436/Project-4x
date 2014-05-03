package com.client;

import static com.google.gwt.query.client.GQuery.$;

import com.client.gameinterface.Console;
import com.client.gameinterface.GameInterface;
import com.client.model.ClientModel;
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

		$("#login-button").click(new Function() {
			public boolean f(Event e) {
				Console.log($("#login-username").val());
				Console.log($("#login-password").val());

				Window.alert("Login?!");

				$("#login-screen").remove();
				// Init game in 1 second
				// (This is purely to see the loading screen)
				Timer t = new Timer() {
					@Override
					public void run() {
						ClientModel theModel = new ClientModel();
						theModel.run();
						GameCanvas canvas = new GameCanvas(theModel);
						GameInterface.init(theModel);
						// Remove loading screen
						$("#loading-screen").remove();
					}
				};
				t.schedule(1000);
				return true;
			}
		});
	}

}
