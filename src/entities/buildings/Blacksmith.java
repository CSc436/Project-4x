package entities.buildings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import control.GameModel;
import entities.research.TechnologyEnum;
import entities.stats.BaseStatsEnum;

public class Blacksmith extends Building implements ResearchBuilding {

	ArrayList<TechnologyEnum> techlist = new ArrayList<TechnologyEnum>();

	public Blacksmith(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.BLACKSMITH, BaseStatsEnum.BLACKSMITH
				.getStats(), BuildingType.BLACKSMITH, xco, yco,
				BuildingType.BLACKSMITH.getX(), BuildingType.BLACKSMITH.getY());

		// A list of available technologies for the building
		this.addTechnology(TechnologyEnum.INFANTRYDAMAGE);
		this.addTechnology(TechnologyEnum.INFANTRYARMOR);
		this.addTechnology(TechnologyEnum.RANGEDLOS);
		this.addTechnology(TechnologyEnum.RANGEDDAMAGE);

	}

	@Override
	public ArrayList<TechnologyEnum> getTechnologyList() {
		return techlist;
	}

	@Override
	public void addTechnology(TechnologyEnum t) {
		techlist.add(t);

	}

	@Override
	public boolean hasTechnologyInList(TechnologyEnum t) {
		return techlist.contains(t);
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

	@Override
	public void tick(int timeScale, GameModel model) {
		// TODO Auto-generated method stub
		
	}

}
