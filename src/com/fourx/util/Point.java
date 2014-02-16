package com.fourx.util;

/**
 * Extends the Point class in gwt but sets x and y to public.
 * @author Colin
 *
 */
public class Point extends com.google.gwt.touch.client.Point {
	public int x;
	public int y;
	
	public Point(int r, int c) {
		super(r, c);
	}
}
