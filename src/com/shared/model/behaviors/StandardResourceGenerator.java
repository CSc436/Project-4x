package com.shared.model.behaviors;

import com.shared.model.resources.Resources;

public class StandardResourceGenerator implements ResourceGenerator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9150257151528863922L;
	private Resources resourcesPerSecond;
	private int gps;
	private int wps;
	private int fps;
	private int sps;
	private int rps;
	
	public StandardResourceGenerator( Resources r ) {
		resourcesPerSecond = r;
		gps = r.getGold();
		wps = r.getWood();
		fps = r.getFood();
		sps = r.getStone();
		rps = r.getResearchPts();
	}
	
	@Override
	public Resources generateResources( int timeStep ) {
		double t = timeStep / 1000.0;
		return new Resources((int) t*gps, (int) t*wps, (int) t*fps, (int) t*sps, (int) t*rps);
	}

}
