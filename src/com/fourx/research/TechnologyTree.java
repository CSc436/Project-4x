package com.fourx.research;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.fourx.annotations.TechDisabledByDefault;

import control.Player;

public class TechnologyTree {
	private Player p;

	public HashMap<String, Technology> technologies;
	private HashMap<String, Integer> currently_researching;
	private HashMap<String, Integer> researched;

	public TechnologyTree(Player player) {
		p = player;

		researched = new HashMap<String, Integer>();
		technologies = new HashMap<String, Technology>();
		currently_researching = new HashMap<String, Integer>();
		for (TechnologyEnum t : TechnologyEnum.values()) {
			Technology tech = null;
			// Don't mind this hack. Move along.
			try {
				Class<? extends Technology> techClass = t.getValue();
				if (!techClass.isAnnotationPresent(TechDisabledByDefault.class)) {
					tech = techClass.newInstance();
					technologies.put(t.name(), tech);
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		p.getCivilization().modifyTechnologies(technologies);
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
			Technology t = technologies.get(name);
			if (p.getResources().spend(t.getCost())) {
				// Set the research to currently researching.
				currently_researching.put(name, t.getResearchTime());
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param time
	 *            - a unit of time that progresses the researching towards
	 *            research completion.
	 */
	public boolean researchStep(int time) {
		boolean completed = false;
		// There most likely won't be that many researches going on at once,
		// updating them all won't cause a problem.
		Set<String> keys = currently_researching.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			completed |= researchStep(key, time);
		}
		return completed;
	}

	/**
	 * 
	 * @param key
	 *            - which research to progress towards completion.
	 * @param time
	 *            - the amount of time to move the research forwards.
	 * 
	 * @return - returns whether a research completed in this time step.
	 */
	private boolean researchStep(String key, int time) {
		Integer value = currently_researching.get(key);

		if (value - time <= 0) {
			// complete the research.
			Technology t = technologies.get(key);
			p.upgrades.addTechnology(t);
			researched.put(key, t.current_level);
			t.completeResearch(p);

			// remove from currently researching.
			currently_researching.remove(key);
			return true;
		}
		// decrease time left.
		currently_researching.put(key, value - time);
		return false;
	}

	public int getTimeRemaining(String key) {
		if (currently_researching.containsKey(key))
			return currently_researching.get(key);
		return -1;
	}

	public int getResearchLevel(String key) {
		if (technologies.containsKey(key))
			return technologies.get(key).current_level;
		return -1;
	}

	/**
	 * 
	 * @param name
	 *            - the name of the technology to check.
	 * @return whether the player can research this technology or not.
	 */
	public boolean can_research(String name) {
		if (technologies.containsKey(name)) {
			Technology t = technologies.get(name);
			if (researched.containsKey(name)) {
				if (researched.get(name).intValue() == t.getMax_level()) {
					return false;
				}
			}
			for (TechnologyEnum tech_name : t.getRequirements()) {
				if (!researched.containsKey(tech_name.name())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
