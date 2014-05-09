package com.shared.model.research;

import com.shared.model.control.Player;
import com.shared.model.entities.GameObjectType;
import com.shared.model.resources.Resources;

public class RangedDamage extends Technology {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1553940276170048103L;

	public RangedDamage() {
		super(3);

		costs[0] = new Resources(50, 50, 50, 50, 50);
		costs[1] = new Resources(100, 100, 100, 100, 100);
		costs[2] = new Resources(150, 150, 150, 150, 150);

		buffstats[0].damage = buffstats[0].damage * 2;
		buffstats[1].damage = buffstats[1].damage * 2;
		buffstats[2].damage = buffstats[2].damage * 2;

		time[0] = 50;
		time[1] = 90;
		time[2] = 180;
		appliesTo.add(GameObjectType.UNIT);
	}

	@Override
	public void completeResearch(Player p) {
		completeResearch();
	}

}
