package com.fourx.research.technologies;

import com.fourx.buffs.UnitType;
import com.fourx.research.Technology;
import com.fourx.resources.Resources;

public class BuildingHealth extends Technology {
	public BuildingHealth() {
		// SUPER constructor takes max level of research.
		super(3);

		costs[0] = new Resources(50, 50, 50, 50);
		costs[1] = new Resources(100, 100, 100, 100);
		costs[2] = new Resources(150, 150, 150, 150);
		
		buffstats[0].max_health = 100;
		buffstats[0].actionSpeed = 0.02f;
		buffstats[1].max_health = 200;
		buffstats[1].actionSpeed = 0.02f;
		buffstats[2].max_health = 250;
		buffstats[2].actionSpeed = 0.02f;

		
		time[0] = 50;
		time[1] = 90;
		time[2] = 180;
		appliesTo.add(UnitType.BUILDING);
	}
}
