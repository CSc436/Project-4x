package com.fourx.research;

import java.util.ArrayList;
import java.util.HashMap;

import com.fourx.buffs.BuffStats;
import com.fourx.buffs.TYPE;

public class Upgrades {
	public HashMap<String, BuffStats> mapping;
	
	public Upgrades() {
		mapping = new HashMap<String, BuffStats>();
		for(TYPE type: TYPE.values()) {
			mapping.put(type.name(), new BuffStats());
		}
	}
	
	public void addTechnology(Technology tech) {
		ArrayList<TYPE> appliesTo = tech.getAppliesTo();
		for(int i = 0; i < appliesTo.size(); i++) {
			BuffStats s = mapping.get(appliesTo.get(i).name());
			s.add(tech.getStats());
		}
	}
	
}
