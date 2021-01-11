package com.game.entities;

import java.io.Serializable;

public class Pit implements Comparable<Pit>, Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pitId;
	private int stoneCount;
	private boolean isKalah;

	public Pit(int pitId, int stoneCount, boolean isKalah) {
		super();
		this.pitId = pitId;
		this.stoneCount = isKalah ? 0 : stoneCount;
		this.isKalah = isKalah;
	}

	public int getPitId() {
		return pitId;
	}

	public int getStoneCount() {
		return stoneCount;
	}

	public boolean isKalah() {
		return isKalah;
	}

	@Override
	public int compareTo(Pit secondPit) {
		return pitId.compareTo(secondPit.getPitId());
	}

	public void makeEmpty() {
		stoneCount=0;
	}

	public StoneOperationResult removeStone(int subtractBy) {
		if(isKalah) {
			return new StoneOperationResult(false, stoneCount);
		}
		stoneCount-=subtractBy;
		return new StoneOperationResult(true, stoneCount);

	}

	public StoneOperationResult addStone(int increasedBy) {
		stoneCount+=increasedBy;
		return new StoneOperationResult(true, stoneCount);
	}
	
	public static class StoneOperationResult{
		private boolean isSuccess;
		private int stoneCount;
		public StoneOperationResult(boolean isSuccess, int stoneCount) {
			super();
			this.isSuccess = isSuccess;
			this.stoneCount = stoneCount;
		}
		public boolean isSuccess() {
			return isSuccess;
		}
		public int getStoneCount() {
			return stoneCount;
		}
	}

	public boolean isEmpty() {
		return stoneCount==0;
	}

}
