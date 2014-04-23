package entities;

import entities.research.TechnologyEnum;

public class TestCivilization extends Civilization {

	public TestCivilization() {
		super();
		research_max_level.put(TechnologyEnum.INFANTRYDAMAGE.name(), 1);
	}

	public void addResearch(String name, int level) {
		research_max_level.put(name, level);
	}
}
