package com.shared.model.commands;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.ResearchBuilding;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.entities.GameObject;
import com.shared.model.research.DisabledTechnology;
import com.shared.model.research.InfantryArmor;
import com.shared.model.research.InfantryDamage;
import com.shared.model.research.RangedDamage;
import com.shared.model.research.RangedLOS;
import com.shared.model.research.TechnologyEnum;

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
