package control;

public enum Actions {
	CREATE, //creating units, buildings
	STARTUP_CREATE, //creating players, maps
	ORDER, //ordering buildings to queue up something to be created
	MOVE, //telling units to move somewhere on map
	ATTACK, //telling units to attack something
	SELECT, //choose unit/building to be initiator of next action
	SELL, //sell buildings for some gold value
	DESTROY, //get rid of unit
	QUIT //start shutdown sequence of game
	;

}
