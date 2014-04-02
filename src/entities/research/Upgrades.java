package entities.research;

import java.util.ArrayList;
import java.util.HashMap;

import entities.GameObjectType;
import entities.stats.BuffStats;

public class Upgrades {
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
