package entities.research;

import control.Player;
import entities.GameObjectType;
import entities.resources.Resources;

public class RangedDamage extends Technology {
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
