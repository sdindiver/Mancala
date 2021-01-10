package com.game.Enum;

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
