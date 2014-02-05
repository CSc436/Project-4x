package com.fourx.research;

import java.util.HashMap;

import com.fourx.Player;

public class TechnologyTree {
	Player p;

	private HashMap<String, Technology> technologies;

	public TechnologyTree(Player player) {
		p = player;
		technologies = new HashMap<String, Technology>();
		for (TechnologyEnum t : TechnologyEnum.values()) {
			Technology tech = t.getValue();
			technologies.put(tech.getName(), tech);
		}
	}

	public boolean research(String name) {
		if (technologies.containsKey(name)) {
			Technology t = technologies.get(name);
			p.upgrades.addTechnology(t);
			return true;
		}
		return false;
	}
}
