package com.fourx.civilizations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.fourx.research.Technology;

public abstract class Civilization {

	HashMap<String, Integer> research_max_level;
	
	public Civilization() {
		research_max_level = new HashMap<String, Integer>();
	}
	
	public HashMap<String, Integer> getMaxLevels() {
		return research_max_level;
	}
	
	/**
	 * Each Civilization can decrease the maximum levels of research available to them.
	 * example: A civilization is bad at infantry, so it only gets 1 research level into infantry damage.
	 * 
	 * @param civ - the civilization passed in from the player object.
	 */
	public void alterMaxLevels(HashMap<String, Technology> technologies) {
		Set<String> level_keys = research_max_level.keySet();
		
		Iterator<String> iter = level_keys.iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			if (technologies.containsKey(key)) {
				Technology t = technologies.get(key);
				
				t.setMax_level(research_max_level.get(key));
			}
		}
	}
}
