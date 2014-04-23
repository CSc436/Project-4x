package entities.research;

import control.Player;
import entities.GameObjectType;
import entities.resources.Resources;

public class RangedLOS extends Technology {
	public RangedLOS() {
		super(3);

		costs[0] = new Resources(50, 50, 50, 50, 50);
		costs[1] = new Resources(100, 100, 100, 100, 100);
		costs[2] = new Resources(150, 150, 150, 150, 150);

		buffstats[0].range = buffstats[0].range * 2;
		buffstats[1].range = buffstats[1].range * 2;
		buffstats[2].range = buffstats[2].range * 2;

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
