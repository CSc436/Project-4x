package com.client;

public class Console {

	public static native void log(String text) 
	/*-{
		console.log(text);
	}-*/;
}
