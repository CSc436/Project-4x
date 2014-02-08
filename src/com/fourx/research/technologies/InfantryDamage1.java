package com.fourx.research.technologies;

import com.fourx.buffs.TYPE;
import com.fourx.research.Technology;

public class InfantryDamage1 extends Technology {

	public InfantryDamage1() {
		super();
		buffstats.damage = 1;
		appliesTo.add(TYPE.INFANTRY);
	}
}
