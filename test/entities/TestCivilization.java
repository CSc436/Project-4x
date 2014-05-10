package entities;

import com.shared.model.entities.Civilization;

public class TestCivilization extends Civilization {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6468932329072965439L;

	public TestCivilization() {
		super();
		//research_max_level.put(TechnologyEnum.INFANTRYDAMAGE.name(), 1);
	}

	public void addResearch(String name, int level) {
		//research_max_level.put(name, level);
	}
}
