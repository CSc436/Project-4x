package com.shared.model.behaviors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.UnitType;

public class StandardProduction implements Producer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -445465287510037092L;
	private UnitType typeInProduction;
	private Queue<UnitType> productionQueue = new LinkedList<UnitType>();
	private Queue<UnitType> finishedUnitTypes = new LinkedList<UnitType>();
	private ArrayList<UnitType> producibleUnits;
	private int productTime;
	private int timeInProduction;
	private boolean activelyProducing;

	public StandardProduction() {
		this.producibleUnits = new ArrayList<UnitType>();
		activelyProducing = false;
	}

	public StandardProduction(ArrayList<UnitType> producibleUnits) {
		this.producibleUnits = producibleUnits;
		activelyProducing = false;
	}

	@Override
	public void queueProduction(UnitType type) {
		if (producibleUnits.contains(type)) {
			if (typeInProduction == null)
				startProducing(type);
			else
				productionQueue.add(type);
		}
	}

	@Override
	public void simulateProduction(int timeStep) {
		if (activelyProducing) {
			timeInProduction += timeStep;
			if (timeInProduction >= productTime * 1000) {
				timeInProduction -= productTime * 1000;
				// @TODO: create unit and place it on map
				finishedUnitTypes.add(typeInProduction);
				if (!productionQueue.isEmpty()) {

					startProducing(productionQueue.remove());
					

				} else {
					timeInProduction = 0;
					activelyProducing = false;
					typeInProduction = null;
				}
			}
		}
	}

	private void startProducing(UnitType type) {
		activelyProducing = true;
		typeInProduction = type;
		timeInProduction = 0;

		switch (type) {
		case MILITIA:
			productTime = BaseStatsEnum.MILITIA.getCreationTime();
			break;
		case INFANTRY:
			productTime = BaseStatsEnum.INFANTRY.getCreationTime();
			break;
		case ARCHER:
			productTime = BaseStatsEnum.ARCHER.getCreationTime();
			break;
		case SKIRMISHER:
			productTime = BaseStatsEnum.SKIRMISHER.getCreationTime();
			break;
		case KNIGHT:
			productTime = BaseStatsEnum.KNIGHT.getCreationTime();
			break;
		case RANGED_CALVARY:
			productTime = BaseStatsEnum.RANGED_CALVARY.getCreationTime();
			break;
		case TRANSPORT:
			productTime = BaseStatsEnum.TRANSPORT.getCreationTime();
			break;
		case CATAPULT:
			productTime = BaseStatsEnum.CATAPULT.getCreationTime();
			break;
		case BATTERING_RAM:
			productTime = BaseStatsEnum.BATTERING_RAM.getCreationTime();
			break;
		case RIFLEMAN:
			productTime = BaseStatsEnum.RIFLEMAN.getCreationTime();
			break;
		case DRAGOON:
			productTime = BaseStatsEnum.DRAGOON.getCreationTime();
			break;
		case CANNON:
			productTime = BaseStatsEnum.CANNON.getCreationTime();
			break;
		case MEDIC:
			productTime = BaseStatsEnum.MEDIC.getCreationTime();
			break;
		case TRADE_CART:
			productTime = BaseStatsEnum.TRADE_CART.getCreationTime();
			break;
		default:
			productTime = 0;
			break;
		}
	}

	@Override
	public Queue<UnitType> getProducedUnits() {
		Queue<UnitType> units = finishedUnitTypes;
		finishedUnitTypes = new LinkedList<UnitType>();
		return units;
	}

}
