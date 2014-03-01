package com.fourx.research;

import java.util.ArrayList;

import com.fourx.buffs.BuffStats;
import com.fourx.buffs.UnitType;
import com.fourx.resources.Resources;

import control.Player;

public abstract class Technology {
	protected ArrayList<TechnologyEnum> requirements;
	protected ArrayList<UnitType> appliesTo;
	
	protected int current_level;
	private int max_level;
	protected Resources[] costs;
	protected BuffStats[] buffstats;
	protected int[] time;
	
	public Technology(int max_levels) {
		requirements = new ArrayList<TechnologyEnum>();
		appliesTo = new ArrayList<UnitType>();
		current_level = 1;
		
		// Multi-level stuff
		setMax_level(max_levels);
		buffstats = new BuffStats[max_levels];
		costs = new Resources[max_levels];
		time = new int[max_levels];
		for(int i = 0; i < max_levels; i++) {
			buffstats[i] = new BuffStats();
		}
	}
	
	public ArrayList<TechnologyEnum> getRequirements(){ return requirements; };
	public ArrayList<UnitType> getAppliesTo(){ return appliesTo; };
	
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

	public int getMax_level() {
		return max_level;
	}

	public void setMax_level(int max_level) {
		this.max_level = max_level;
	}
}