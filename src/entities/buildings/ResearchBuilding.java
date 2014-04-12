package entities.buildings;

import java.util.ArrayList;
import entities.research.TechnologyEnum;

public interface ResearchBuilding {

	/*
	 * ArrayList<TechnologyEnum> researchables;
	 * 
	 * public ResearchBuilding(UUID id, int playerId, BaseStatsEnum baseStats,
	 * UnitStats new_stats, BuildingType buildingType, float xco, float yco, int
	 * height, int width) { super(id, playerId, baseStats, new_stats,
	 * buildingType, xco, yco, height, width); researchables = new
	 * ArrayList<TechnologyEnum>(); }
	 */

	public ArrayList<TechnologyEnum> getTechnologyList();;

	public void addTechnology(TechnologyEnum t);

	public void advanceResearchProduction(int timestep);
}
