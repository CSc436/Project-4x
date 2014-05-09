package com.shared.utils;

import java.io.Serializable;

public class ColorFunctions implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8961374914953537376L;

	public static float intToHue( int i ) {
		int r = Integer.reverse(i << 1);
		return 1.0f * r / Integer.MAX_VALUE;
	}
	
	public static float[] hsvToRgb(float hue, float saturation, float value) {

	    int h = (int)(hue * 6);
	    float f = hue * 6 - h;
	    float p = value * (1 - saturation);
	    float q = value * (1 - f * saturation);
	    float t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return new float[] {value, t, p};
	      case 1: return new float[] {q, value, p};
	      case 2: return new float[] {p, value, t};
	      case 3: return new float[] {p, q, value};
	      case 4: return new float[] {t, p, value};
	      case 5: return new float[] {value, p, q};
	      default: return new float[] {0, 0, 0};
	    }
	}
	
	public static float[] intToHSV( int i ) {
		return hsvToRgb(intToHue(i), 1, 1);
	}
	
}
