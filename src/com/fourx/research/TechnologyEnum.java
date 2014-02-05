package com.fourx.research;

import com.fourx.research.technologies.InfantryDamage1;

public enum TechnologyEnum {
	INFANTRYDAMAGE1(new InfantryDamage1());
	
	private Technology value;
	private TechnologyEnum(Technology tech) {
		value = tech;
	}
	
	public Technology getValue() {
		return value;
	}
}
