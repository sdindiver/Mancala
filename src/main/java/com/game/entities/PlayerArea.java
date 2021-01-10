package com.game.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.game.model.PlayerAreaInfo;

public final class PlayerArea {

	private Map<Integer,Integer> pitStoneCountMap = new TreeMap<>();
	
	private PlayerArea(Map<Integer,Integer>  pitStoneCntMap) {
		this.pitStoneCountMap = pitStoneCntMap;
	}
	
	public static PlayerArea getArea(PlayerAreaInfo areaInfo) {
		Map<Integer,Integer> pitStoneCountMap = new HashMap<>();
		int pitStartIdx = areaInfo.getSeatType().getPitStartIdx();
		pitStoneCountMap.put(pitStartIdx++,areaInfo.getGameType().getStoneCount());
		pitStoneCountMap.put(pitStartIdx++,areaInfo.getGameType().getStoneCount());
		pitStoneCountMap.put(pitStartIdx++,areaInfo.getGameType().getStoneCount());
		pitStoneCountMap.put(pitStartIdx++,areaInfo.getGameType().getStoneCount());
		pitStoneCountMap.put(pitStartIdx++,areaInfo.getGameType().getStoneCount());
		pitStoneCountMap.put(pitStartIdx++,areaInfo.getGameType().getStoneCount());
		pitStoneCountMap.put(pitStartIdx++,0);	
		return new PlayerArea(pitStoneCountMap);
	}
}
