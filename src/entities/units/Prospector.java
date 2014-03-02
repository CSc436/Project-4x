package entities.units;

import control.Player;

/**
 * 
 * @author NRTopping
 * @class Prospector
 * @summary 
 * The prospector is an agent that controls serfs/towns people and 
 * directs them to collect resources in nearby areas. The prospector
 * will set out and explore for more resources if the surrounding area
 * becomes depleted. 
 * The prospector will have the ability - if the player so chooses - to
 * build resource generating buildings. 
 */
public class Prospector extends Agent{

	
	public Prospector(Player p, int idno) {
		super(p, idno);
		// TODO Auto-generated constructor stub
	}

}
