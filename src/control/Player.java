package control;

import com.fourx.civilizations.Civilization;
import com.fourx.civilizations.PerfectCivilization;
import com.fourx.research.TechnologyTree;
import com.fourx.research.Upgrades;
import com.fourx.resources.Resources;


/* state
based on state, the unit will:
continue following orders
interrupt current orders for an active action
allow other object to give unit an order
*/


import entities.PlayerUnits;

public class Player {

	private final String name;

	// select and deselect
	private PlayerUnits objects;
	public TechnologyTree techTree;
	public Upgrades upgrades;
	private Resources resources;
	private Civilization civ;

	// Bare constructor
	public Player() {
		this("", new PerfectCivilization());
	}

	public Player(String alias, Civilization civ) {
		this(alias, new Resources(0, 0, 0, 0), civ);
	}

	public Player(String alias, Resources resources, Civilization civ) {
		name = alias;
		// civilization
		this.civ = civ;

		// resources
		this.resources = resources;

		// units
		objects = new PlayerUnits();

		// technology
		upgrades = new Upgrades();
		techTree = new TechnologyTree(this);
	}

	public String getAlias() {
		return name;
	}
	
	public PlayerUnits getUnits() {
		return objects;
	}

	public Resources getResources() {
		return resources;
	}

	public TechnologyTree getTechTree() {
		return techTree;
	}

	public Civilization getCivilization() {
		return civ;
	}
}
