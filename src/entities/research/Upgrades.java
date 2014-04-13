package entities.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import entities.GameObjectType;
import entities.stats.BuffStats;

public class Upgrades implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5748974532072650087L;
	public HashMap<String, BuffStats> mapping;
	
	public Upgrades() {
		mapping = new HashMap<String, BuffStats>();
		for(GameObjectType type: GameObjectType.values()) {
			mapping.put(type.name(), new BuffStats());
		}
	}
	
	public void addTechnology(Technology tech) {
		ArrayList<GameObjectType> appliesTo = tech.getAppliesTo();
		for(int i = 0; i < appliesTo.size(); i++) {
			BuffStats s = mapping.get(appliesTo.get(i).name());
			s.add(tech.getStats());
		}
	}
	
}
