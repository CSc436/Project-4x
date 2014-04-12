package entities.buildings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import entities.research.Technology;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;


public class ResearchBuilding extends Building {

	ArrayList<Technology> researchables;

	public ResearchBuilding(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, BuildingType buildingType, float xco,
			float yco, int height, int width) {
		super(id, playerId, baseStats, new_stats, buildingType, xco, yco,
				height, width);
		researchables = new ArrayList<Technology>();
	}

	@Override
	protected void setActions() {
		// TODO Auto-generated method stub

	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Technology> getTechnologyList() {

		return researchables;

	}

	public void addTechnology(Technology t) {
		researchables.add(t);
	}

	public boolean removeTechnology(Technology t) {
		return researchables.remove(t);
	}

	public void advanceProduction(int timestep) {
		for (Technology t : researchables) {
			t.advanceTimeStep(timestep);
			System.out.println("time step to" + t.getResearchTime());
		}
		return;
	}
}
