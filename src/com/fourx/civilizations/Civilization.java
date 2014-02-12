package com.fourx.civilizations;

import java.util.HashMap;

public abstract class Civilization {

	HashMap<String, Integer> research_max_level;
	
	public Civilization() {
		research_max_level = new HashMap<String, Integer>();
	}
	
	public HashMap<String, Integer> getMaxLevels() {
		return research_max_level;
	}
}
