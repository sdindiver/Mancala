package com.game.Enum;

/** Kalah game can be 2/4/6 stone types
 * @author indiv
 *
 */
public enum KalahGameType {

	THREE(3),FOUR(4),SIX(6);
	private int stoneCount;

	private KalahGameType(int stoneCount) {
		this.stoneCount = stoneCount;
	}

	public int getStoneCount() {
		return stoneCount;
	}
	
}
