package control;

import entities.Action;
import entities.Civilization;
import entities.PerfectCivilization;
import entities.PlayerUnits;
import entities.research.TechnologyTree;
import entities.research.Upgrades;
import entities.resources.Resources;
import entities.units.Unit;

public class Player {

	private final String name;
	private int id;

	private PlayerUnits objects; // the objects that the player owns, including
									// the currently selected units

	public TechnologyTree techTree;
	public Upgrades upgrades;
	public static Resources resources;
	private Civilization civ;
	private CommandQueue cq;

	// Bare constructor
	public Player(String name, int id) {
		this(name, id, new PerfectCivilization());
	}

	public Player(String alias, int id, Civilization civ) {
		this(alias, id, civ, new Resources(0, 0, 0, 0));
	}

	public Player(String alias, int id, Civilization civ, Resources resources) {
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

	public PlayerUnits getGameObjects() {
		return objects;
	}

	public static Resources getResources() {
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
//		u.addAction(a);
	}

}
