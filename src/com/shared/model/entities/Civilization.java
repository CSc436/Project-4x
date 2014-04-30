package com.shared.model.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.shared.model.research.Technology;
import com.shared.model.research.TechnologyEnum;

public abstract class Civilization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4980684434586151255L;
	HashMap<String, Integer> research_max_level;

	public Civilization() {
		research_max_level = new HashMap<String, Integer>();
	}

	/**
	 * Each Civilization can decrease the maximum levels of research available
	 * to them. example: A civilization is bad at infantry, so it only gets 1
	 * research level into infantry damage.
	 * 
	 * @param civ
	 *            - the civilization passed in from the player object.
	 */
	public void modifyTechnologies(HashMap<String, Technology> technologies) {
		Set<String> level_keys = research_max_level.keySet();

		Iterator<String> iter = level_keys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			// if the technology already exists within available technologies.
			// set the max level
			if (technologies.containsKey(key)) {
				Technology t = technologies.get(key);
				t.setMax_level(research_max_level.get(key));
			} else if (TechnologyEnum.valueOf(key) != null) {
				// otherwise attempt to add this technology with the specified
				// max_level to technologies.
				TechnologyEnum te = TechnologyEnum.valueOf(key);
				try {
					System.out.println("Adding Technology: "
							+ te.getValue().getName());
					Technology toAdd = te.getValue().newInstance();
					toAdd.setMax_level(research_max_level.get(key));
					technologies.put(key, toAdd);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
