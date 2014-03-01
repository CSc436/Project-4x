package control;




/* state
 based on state, the unit will:
 continue following orders
 interrupt current orders for an active action
 allow other object to give unit an order
 */




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

	private PlayerUnits objects; // the objects that the player owns, including
									// the currently selected units

	public TechnologyTree techTree;
	public Upgrades upgrades;
	private Resources resources;
	private Civilization civ;
	private CommandQueue cq;

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
		cq = new CommandQueue();
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

	public CommandQueue getCommandQueue() {

		return cq;
	}

	public void addActionTo(Unit u, Action a) {

		cq.push(a, u);
		u.addAction(a);
	}
	
	
	
}
