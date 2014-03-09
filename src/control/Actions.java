package control;

public enum Actions {
	CREATE_UNIT, // creating units
	CREATE_BUILDING, // create building -> PAYLOAD : playerid, unitType, Point
	STARTUP_CREATE, // creating players, maps
	ORDER, // ordering buildings to queue up something to be created
	MOVE, // telling units to move somewhere on map
	ATTACK, // telling units to attack something
	SELECT, // choose unit/building to be initiator of next action
	DESELECT, // delect anything selected
	SELL, // sell buildings for some gold value
	DESTROY, // get rid of unit
	QUIT, // start shutdown sequence of game

	;

}
