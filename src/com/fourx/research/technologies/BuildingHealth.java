package com.fourx.research.technologies;

import com.fourx.buffs.TYPE;
import com.fourx.research.Technology;
import com.fourx.resources.Resources;

public class BuildingHealth extends Technology {
	public BuildingHealth() {
		// SUPER constructor takes max level of research.
		super(3);

		costs[0] = new Resources(50, 50, 50, 50);
		costs[1] = new Resources(100, 100, 100, 100);
		costs[2] = new Resources(150, 150, 150, 150);
		
		buffstats[0].health = 100;
		buffstats[0].actionSpeedModifier = 0.02f;
		buffstats[1].health = 200;
		buffstats[1].actionSpeedModifier = 0.02f;
		buffstats[2].health = 250;
		buffstats[2].actionSpeedModifier = 0.02f;

		
		time[0] = 50;
		time[1] = 90;
		time[2] = 180;
		appliesTo.add(TYPE.BUILDING);
	}
}
