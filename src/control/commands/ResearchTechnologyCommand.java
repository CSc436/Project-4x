package control.commands;

import control.GameModel;
import control.Player;
import entities.buildings.ResearchBuilding;
import entities.research.DisabledTechnology;
import entities.research.InfantryArmor;
import entities.research.InfantryDamage;
import entities.research.RangedDamage;
import entities.research.RangedLOS;
import entities.research.TechnologyEnum;

public class ResearchTechnologyCommand implements Command {

	private Player p;
	private ResearchBuilding b;
	private TechnologyEnum t;

	public ResearchTechnologyCommand(Player p, ResearchBuilding b,
			TechnologyEnum t) {

		this.p = p;
		this.b = b;
		this.t = t;

	}

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performCommand(GameModel model) {
		// if the building is owned by the calling player, add the technology to
		// the buildings tech list
		if (b.getPlayer() == p) {

			switch (t) {

			case INFANTRYDAMAGE:
				b.addTechnology(new InfantryDamage());
				break;
			case DISABLEDTECHNOLOGY:
				b.addTechnology(new DisabledTechnology());
				break;
			case RANGEDDAMAGE:
				b.addTechnology(new RangedDamage());
				break;
			case RANGEDLOS:
				b.addTechnology(new RangedLOS());
				break;
			case INFANTRYARMOR:
				b.addTechnology(new InfantryArmor());
				break;
			default:
				break;

			}

		}
		return false;
	}

}
