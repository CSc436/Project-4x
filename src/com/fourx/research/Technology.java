package com.fourx.research;

import java.util.ArrayList;

import com.fourx.Player;
import com.fourx.buffs.BuffStats;
import com.fourx.buffs.TYPE;
import com.fourx.resources.Resources;

public abstract class Technology {
	protected ArrayList<TechnologyEnum> requirements;
	protected ArrayList<TYPE> appliesTo;
	
	protected int current_level;
	protected int max_level;
	protected Resources[] costs;
	protected BuffStats[] buffstats;
	protected int[] time;
	
	public Technology(int max_levels) {
		requirements = new ArrayList<TechnologyEnum>();
		appliesTo = new ArrayList<TYPE>();
		current_level = 1;
		
		// Multi-level stuff
		max_level = max_levels;
		buffstats = new BuffStats[max_levels];
		costs = new Resources[max_levels];
		time = new int[max_levels];
		for(int i = 0; i < max_levels; i++) {
			buffstats[i] = new BuffStats();
		}
	}
	
	public ArrayList<TechnologyEnum> getRequirements(){ return requirements; };
	public ArrayList<TYPE> getAppliesTo(){ return appliesTo; };
	
	public BuffStats getStats(){ return buffstats[current_level - 1]; }
	public Resources getCost() { return costs[current_level - 1]; }
	
	public void completeResearch() {
		current_level++;
	}

	public Integer getResearchTime() {
		return time[current_level - 1];
	}

	public void completeResearch(Player p) {
		completeResearch();
	}
}