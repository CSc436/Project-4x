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
	 * @return - whether the research succeeded or not.
	 */
	public boolean research(String name) {
		if (can_research(name)) {
			p.upgrades.addTechnology(technologies.get(name));
			researched.add(name);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param name
	 *            - the name of the technology to check.
	 * @return whether the player can research this technology or not.
	 */
	public boolean can_research(String name) {
		if (technologies.containsKey(name) && !researched.contains(name)) {
			Technology t = technologies.get(name);
			for (TechnologyEnum tech_name : t.getRequirements()) {
				if (!researched.contains(tech_name.name())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
