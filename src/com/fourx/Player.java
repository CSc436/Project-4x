package com.fourx;
import com.fourx.civilizations.Civilization;
import com.fourx.civilizations.PerfectCivilization;
import com.fourx.research.TechnologyTree;
import com.fourx.research.Upgrades;
import com.fourx.resources.Resources;

public class Player {

	private final String name;
	public TechnologyTree techTree;
	public Upgrades upgrades;
	private Resources resources;
	private Civilization civ;

	
//	Bare constructor
	public Player(){
		this("", new PerfectCivilization());
	}
	
	public Player(String alias, Civilization civ) {
		this(alias, new Resources(0,0,0,0), civ);
	}
	
	public Player(String alias, Resources resources, Civilization civ){
		name = alias;
		this.civ = civ;
		
		this.resources = resources;
		upgrades = new Upgrades();
		techTree = new TechnologyTree(this);
	}
	
	public String getAlias(){
		return name;
	}
	
	public Resources getResources() { return resources; }
	public TechnologyTree getTechTree() { return techTree; }
	public Civilization getCivilization() { return civ; }
	
}
