package com.fourx.research;

import java.util.ArrayList;
import java.util.HashMap;

import com.fourx.Player;

public class TechnologyTree {
	Player p;

	private HashMap<String, Technology> technologies;
	private ArrayList<String> researched;

	public TechnologyTree(Player player) {
		p = player;

		researched = new ArrayList<String>();
		technologies = new HashMap<String, Technology>();
		for (TechnologyEnum t : TechnologyEnum.values()) {
			Technology tech = t.getValue();
			technologies.put(t.name(), tech);
		}
	}

	/**
	 * 
	 * @param name
	 *            - the name of the technology to research. This name must match
	 *            a TechnologyEnum
	 * @return
	 */
	public boolean research(String name) {
		if (technologies.containsKey(name) && !researched.contains(name)) {
			Technology t = technologies.get(name);
			boolean requirements_researched = true;
			for (TechnologyEnum tech_name : t.getRequirements()) {
				if (!researched.contains(tech_name.name())) {
					requirements_researched = false;
					break;
				}
			}
			if (requirements_researched) {
				p.upgrades.addTechnology(t);
				researched.add(name);
				return true;
			}
			return false;
		}
		return false;
	}
}
