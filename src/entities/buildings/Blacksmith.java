package entities.buildings;

import java.util.UUID;

import entities.research.InfantryArmor;
import entities.research.InfantryDamage;
import entities.research.RangedDamage;
import entities.research.RangedLOS;
import entities.research.Technology;
import entities.stats.BaseStatsEnum;

public class Blacksmith extends ResearchBuilding {

	public Blacksmith(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.BLACKSMITH, BaseStatsEnum.BLACKSMITH
				.getStats(), BuildingType.BLACKSMITH, xco, yco,
				BuildingType.BLACKSMITH.getX(), BuildingType.BLACKSMITH.getY());

	}

}
