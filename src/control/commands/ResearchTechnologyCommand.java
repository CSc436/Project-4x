package control.commands;

import control.GameModel;
import control.Player;
import entities.GameObject;
import entities.buildings.Building;
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
		// building must be owned by the player
		// the building must have the technology in its list
		// the player must be able to research the technology
		// if these conditions are true, the research is added to the player
		if (p == b.getPlayer()) {
			if (b.hasTechnologyInList(t)) {
				if (p.getTechTree().can_research(t.toString())) {
					p.getTechTree().research(t.name());
				}
			}
		}

		return false;
	}

}
