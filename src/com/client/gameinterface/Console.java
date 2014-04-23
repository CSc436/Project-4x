package com.client.gameinterface;

public class Console {

	public static native void log(String text) 
	/*-{
		console.log(text);
	}-*/;
}
