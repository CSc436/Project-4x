package com.fourx;
import com.fourx.research.TechnologyTree;
import com.fourx.research.Upgrades;

public class Player {

	private String name;
	private TechnologyTree techTree;
	public Upgrades upgrades;

	public Player(String alias) {
		name = alias;
		upgrades = new Upgrades();
		techTree = new TechnologyTree(this);
	}
	
	public void research(String name) {
		techTree.research(name);
	}

}
