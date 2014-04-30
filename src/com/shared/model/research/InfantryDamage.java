package com.shared.model.research;

import com.shared.model.control.Player;
import com.shared.model.entities.GameObjectType;
import com.shared.model.resources.Resources;

public class InfantryDamage extends Technology {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4701049621446129892L;

	public InfantryDamage() {
		// SUPER constructor takes max level of research.
		super(3);

		costs[0] = new Resources(50, 50, 50, 50, 50);
		costs[1] = new Resources(100, 100, 100, 100, 100);
		costs[2] = new Resources(150, 150, 150, 150, 150);
		
		buffstats[0].damage = 1;
		buffstats[1].damage = 10;
		buffstats[2].damage = 100;
		
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
