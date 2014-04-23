package com.client;
import static com.google.gwt.query.client.GQuery.$;

import java.util.Random;

import com.client.gameinterface.GameInterface;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */


public class PlayGame implements EntryPoint {

	
	public void onModuleLoad() {
		
		ClientModel theModel = new ClientModel();
		theModel.run();
		GameCanvas canvas = new GameCanvas(theModel);
		GameInterface.init();
		// Remove loading screen
		$("#loading-screen").remove();
	}
}
