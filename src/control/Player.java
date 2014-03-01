package control;

import java.util.ArrayList;

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

import entities.Action;
import entities.PlayerUnits;
import entities.units.Unit;

public class Player {

	private final String name;
	private int id;

	private PlayerUnits objects; // the objects that the player owns, including
									// the currently selected units

	public TechnologyTree techTree;
	public Upgrades upgrades;
	private Resources resources;
	private Civilization civ;
	private CommandQueue cq;

	// Bare constructor
	public Player(String name, int id) {
		this(name, new PerfectCivilization(), id);
	}

	public Player(String alias, Civilization civ, int id) {
		this(alias, new Resources(0, 0, 0, 0), civ, id);
	}

	public Player(String alias, Resources resources, Civilization civ, int id) {
		name = alias;
		// civilization

		this.civ = civ;

		// resources
		this.resources = resources;

		// units
		objects = new PlayerUnits(id);

		// technology
		upgrades = new Upgrades();
		techTree = new TechnologyTree(this);
		cq = new CommandQueue();
		this.id = id;
	}

	public String getAlias() {
		return name;
	}
	
	public int getId() {
		return id;
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

	public CommandQueue getCommandQueue() {

		return cq;
	}

	public void addActionTo(Unit u, Action a) {

		cq.push(a, u);
		u.addAction(a);
	}
	
	
	
}
