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
	private double timeStepTracker;
	
	public StandardResourceGenerator( Resources r ) {
		timeStepTracker = 0.0;
		resourcesPerSecond = r;
		gps = r.getGold();
		wps = r.getWood();
		fps = r.getFood();
		sps = r.getStone();
		rps = r.getResearchPts();
	}
	
	@Override
	public Resources generateResources( int timeStep ) {
		/** NOTE: resources now generate every 10 seconds **/
		timeStepTracker += timeStep;
		double t = timeStepTracker / 10000.0;
		if (timeStepTracker > 10000.0) {
			// If we've reached 10s, send off resources
			timeStepTracker = 0.0;
			return new Resources((int) t*gps, (int) t*wps, (int) t*fps, (int) t*sps, (int) t*rps);
		}
		// Otherwise, send off no resources
		return new Resources(0, 0, 0, 0, 0);
	}

}
