package entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import control.UnitType;
import entities.stats.BaseStatsEnum;

public class ProductionBehavior implements ObjectBehavior {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -445465287510037092L;
	private UnitType inProduction;
	private Queue<UnitType> productionQueue = new LinkedList<UnitType>();
	private ArrayList<UnitType> producibleUnits;
	private int productTime;
	private int timeInProduction;
	private boolean activelyProducing;
	
	public ProductionBehavior() {
		this.producibleUnits = new ArrayList<UnitType>();
		activelyProducing = false;
	}
	
	public ProductionBehavior( ArrayList<UnitType> producibleUnits) {
		this.producibleUnits = producibleUnits;
		activelyProducing = false;
	}
	
	public void produce( UnitType type ) {
		if(producibleUnits.contains(type)) {
			if( inProduction == null ) startProducing(type);
			else productionQueue.add(type);
		}
	}
	
	@Override
	public void advanceTimeStep(int timeStep) {
		if(activelyProducing) {
			timeInProduction += timeStep;
			if(timeInProduction >= productTime * 1000) {
				timeInProduction -= productTime * 1000;
				// @TODO: create unit and place it on map
				if( !productionQueue.isEmpty() )
					startProducing( productionQueue.remove() );
				else {
					timeInProduction = 0;
					activelyProducing = false;
				}
			}
		}
	}
	
	public void startProducing( UnitType type ) {
		activelyProducing = true;
		inProduction = type;
		timeInProduction = 0;
		
		switch (type) {
			case MILITIA: 
				productTime = BaseStatsEnum.MILITIA.getCreationTime(); break;
			case INFANTRY: 
				productTime = BaseStatsEnum.INFANTRY.getCreationTime(); break;
			case ARCHER: 
				productTime = BaseStatsEnum.ARCHER.getCreationTime(); break;
			case SKIRMISHER: 
				productTime = BaseStatsEnum.SKIRMISHER.getCreationTime(); break;
			case KNIGHT: 
				productTime = BaseStatsEnum.KNIGHT.getCreationTime(); break;
			case RANGED_CALVARY: 
				productTime = BaseStatsEnum.RANGED_CALVARY.getCreationTime(); break;
			case TRANSPORT: 
				productTime = BaseStatsEnum.TRANSPORT.getCreationTime(); break;
			case CATAPULT: 
				productTime = BaseStatsEnum.CATAPULT.getCreationTime(); break;
			case BATTERING_RAM: 
				productTime = BaseStatsEnum.BATTERING_RAM.getCreationTime(); break;
			case RIFLEMAN: 
				productTime = BaseStatsEnum.RIFLEMAN.getCreationTime(); break;
			case DRAGOON: 
				productTime = BaseStatsEnum.DRAGOON.getCreationTime(); break;
			case CANNON: 
				productTime = BaseStatsEnum.CANNON.getCreationTime(); break;
			case MEDIC: 
				productTime = BaseStatsEnum.MEDIC.getCreationTime(); break;
			case TRADE_CART: 
				productTime = BaseStatsEnum.TRADE_CART.getCreationTime(); break;
			default:
				productTime = 0; break;
		}
	}

}
