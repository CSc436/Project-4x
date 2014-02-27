package com.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */


public class PlayGame implements EntryPoint {

	public void onModuleLoad() {
		GameCanvas canvas = new GameCanvas();
		Interface.init();
	}
}
