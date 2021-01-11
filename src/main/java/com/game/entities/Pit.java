package com.game.entities;

import java.io.Serializable;

/**
 * Pit of specific player area
 * 
 * @author indiv
 *
 */
public class Pit implements Comparable<Pit>, Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pitId;
	private int stoneCount;
	private boolean isKalah;

	public Pit(int pitId, int stoneCount) {
		super();
		this.pitId = pitId;
		this.isKalah = this.pitId == 7 || this.pitId == 14;
		this.stoneCount = isKalah ? 0 : stoneCount;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pit other = (Pit) obj;
		if (pitId == null) {
			if (other.pitId != null)
				return false;
		} else if (!pitId.equals(other.pitId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Pit secondPit) {
		return pitId.compareTo(secondPit.getPitId());
	}

	public void makeEmpty() {
		stoneCount = 0;
	}

	public StoneOperationResult addStone(int increasedBy) {
		stoneCount += increasedBy;
		return new StoneOperationResult(true, stoneCount);
	}

	public static class StoneOperationResult {
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
		return stoneCount == 0;
	}

}
