package entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import control.Factory;
import control.UnitType;
import entities.buildings.Building;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class ProductionBehavior implements Producer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -445465287510037092L;
	private Building producer;
	private UnitType typeInProduction;
	private Queue<UnitType> productionQueue = new LinkedList<UnitType>();
	private Queue<Unit> finishedUnits = new LinkedList<Unit>();
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
	
	@Override
	public void queueProduction( UnitType type ) {
		if(producibleUnits.contains(type)) {
			if( typeInProduction == null ) startProducing(type);
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
				float x = (float) producer.getMoveBehavior().getPosition().getX();
				float y = (float) producer.getMoveBehavior().getPosition().getY();
				finishedUnits.add( Factory.buildUnit(null, 0, typeInProduction, x, y - 1.0F) );
				if( !productionQueue.isEmpty() )
					startProducing( productionQueue.remove() );
				else {
					timeInProduction = 0;
					activelyProducing = false;
				}
			}
		}
	}
	
	private void startProducing( UnitType type ) {
		activelyProducing = true;
		typeInProduction = type;
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

	@Override
	public Queue<Unit> getProducedUnits() {
		Queue<Unit> units = finishedUnits;
		finishedUnits = new LinkedList<Unit>();
		return units;
	}
	
	

}
