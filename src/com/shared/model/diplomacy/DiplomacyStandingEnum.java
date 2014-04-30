package com.shared.model.diplomacy;

import java.io.Serializable;

/**
 * Public Inner Enum Standing
 * 
 * @author Colin
 * 
 * @description - The possible standings a player can have to another. This
 *              class is used to check whether a player's units should attack
 *              another player's units.
 */
public enum DiplomacyStandingEnum implements Serializable {
	Allied, Neutral, Enemy;
}