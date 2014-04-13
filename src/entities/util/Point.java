package entities.util;

import java.io.Serializable;

/**
 * Extends the Point class in gwt but sets x and y to public.
 * 
 * @author Colin
 * 
 */
public class Point implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5655210724861551612L;
	public float x;
	public float y;

	public Point(float r, float c) {
		x = r;
		y = c;
	}
}
