package entities.gameboard;
/**
 * Resource
 * @author NRTop
 * enum that specifies the type of resource a tile can have.
 */
public enum Resource {
	//  weighted none resources
	// independent weights per resource
	// gold < wood, etc.
	GOLD, WOOD, FOOD, STONE, RESEARCH, NONE;
}
