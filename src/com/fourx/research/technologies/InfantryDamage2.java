package com.fourx.research.technologies;

import com.fourx.buffs.TYPE;
import com.fourx.research.Technology;
import com.fourx.research.TechnologyEnum;

public class InfantryDamage2 extends Technology {

	public InfantryDamage2() {
		super();
		requirements.add(TechnologyEnum.INFANTRYDAMAGE1);
		buffstats.damage = 1;
		appliesTo.add(TYPE.INFANTRY);
	}
}
