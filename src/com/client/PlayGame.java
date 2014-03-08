package com.client;

import com.client.model.ClientModel;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */


public class PlayGame implements EntryPoint {

	public void onModuleLoad() {
		ClientModel theModel = new ClientModel();
		theModel.run();
		GameCanvas canvas = new GameCanvas(theModel);
		Interface.init();
	}
}
