package com.fourx.research;

import java.util.ArrayList;

import com.fourx.buffs.BuffStats;
import com.fourx.buffs.TYPE;

public abstract class Technology {
	public BuffStats buffstats;
	protected ArrayList<TechnologyEnum> requirements;
	protected ArrayList<TYPE> appliesTo;
	
	public Technology() {
		requirements = new ArrayList<TechnologyEnum>();
	}
	
	public ArrayList<TechnologyEnum> getRequirements(){ return requirements; };
	public ArrayList<TYPE> getAppliesTo(){ return appliesTo; };
	public BuffStats getStats(){ return buffstats; };
}