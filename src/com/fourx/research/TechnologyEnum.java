package com.fourx.research;

import com.fourx.research.technologies.*;

public enum TechnologyEnum {
	INFANTRYDAMAGE1(InfantryDamage.class);

	private Class<Technology> value;

	/**
	 * 
	 * @param tech
	 *            - Which class we would like to instantiate in the
	 *            TechnologyTree class. We cannot instantiate the class here,
	 *            because then we end up with only 1 instance of each
	 *            Technology, but would still like to define them in this class.
	 */
	private TechnologyEnum(Class<?> tech) {
		value = (Class<Technology>) tech;
	}

	public Class<Technology> getValue() {
		return value;
	}
}
