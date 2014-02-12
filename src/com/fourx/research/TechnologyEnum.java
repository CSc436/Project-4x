package com.fourx.research;

import com.fourx.research.technologies.*;

public enum TechnologyEnum {
	INFANTRYDAMAGE1(InfantryDamage.class);
	
	private Class<Technology> value;
	private TechnologyEnum(Class tech) {
		value = tech;
	}
	
	public Class<Technology> getValue() {
		return value;
	}
}
