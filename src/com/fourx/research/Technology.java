package com.fourx.research;

import java.util.ArrayList;

import com.fourx.buffs.BuffStats;
import com.fourx.buffs.TYPE;
import com.fourx.Requirements;

public abstract class Technology {
	public String name;
	public BuffStats buffstats;
	private ArrayList<Requirements> requirements;
	protected ArrayList<TYPE> appliesTo;
	
	public String getName(){ return name; };
	public void setName(String name){ this.name = name; };
	
	public ArrayList<Requirements> getRequirements(){ return requirements; };
	public ArrayList<TYPE> getAppliesTo(){ return appliesTo; };
	public BuffStats getStats(){ return buffstats; };
}