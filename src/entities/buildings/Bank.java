package entities.buildings;

import java.util.HashMap;

import com.fourx.buffs.UnitType;
import com.fourx.resources.Resources;

import control.Player;
import entities.BaseStatsEnum;

public class Bank extends ResourceBuilding {
	// Placeholder bank building dimensions
	private static final int bank_Height = 4;
	private static final int bank_Width = 4;
	private static final int bank_BaseGenAmt = 100; // Base amount that banks
													// will generate for the
													// user

	public Bank(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int height, int width) {
		super(p, baseStats, type, xco, yco, bank_Height, bank_Width, new Resources(0,bank_BaseGenAmt,0,0));
	}
	
	@Override
	public String toString() {
		return "GameObject type: Bank; Player owner Id: " + this.getPlayerId() + "; Castle id: " + this.getCastleId() 
				+ "; GameObject id: " + this.getId();
	}

	@Override
	protected void setActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}
}
