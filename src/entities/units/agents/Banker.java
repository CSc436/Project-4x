package entities.units.agents;

import java.util.UUID;

import control.Player;
import control.UnitType;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Agent;

/**
 * 
 * @author NRTopping
 * @class Banker
 * @summmary
 * The Banker class is an agent who will manage trade (possibly taxation? 
 * Purchasing armies, developing buildings?). 
 */
public class Banker extends Agent{

	public Banker(Player p, int idno) {
		super(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, GameObjectType type, UnitType unitType, float xco,
			float yco);
		// TODO Auto-generated constructor stub
	}

	/*
	 * TODO add option to initiate trade, should be with an ally or city of own player
	 * 
	 * 
	 */
	
}
