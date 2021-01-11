package com.game.entities;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.game.context.KalahContext;
import com.game.entities.Pit.StoneOperationResult;
import com.game.model.PlayerAreaInfo;

public final class PlayerArea {

	private Map<Integer,Pit> pitIdPitMap = new TreeMap<>();
	
	private PlayerArea(Map<Integer,Pit> pits) {
		this.pitIdPitMap = pits;
	}
	
	public static PlayerArea getArea(PlayerAreaInfo areaInfo) {
		Map<Integer,Pit> pitIdPitMap = new TreeMap<>();
		int pitStartIdx = areaInfo.getSeatType().getPitStartIdx();
		for(int i=1;i<=7;i++) {
			int id = pitStartIdx++;
			pitIdPitMap.put(id,new Pit(id, areaInfo.getGameType().getStoneCount()));
		}
		return new PlayerArea(pitIdPitMap);
	}
	
	
	public Pit getPit(int pitId) {
		return this.pitIdPitMap.get(pitId);
	}
	
	public StoneOperationResult addStone(int pitId, int increasedBy) {
		Pit pit = getPit(pitId);
		if(KalahContext.getCurrentTurnPlayerArea() != this && pit.isKalah()) {
			new StoneOperationResult(false, pit.getStoneCount());
		}
		return pit.addStone(increasedBy);
	}

	public StoneOperationResult removeStone(int pitId, int subtractBy) {
		Pit pit = getPit(pitId);
		return pit.removeStone(subtractBy);
	}

	public boolean isEmpty() {
		Optional<Pit> pit = pitIdPitMap.values().stream().filter(x-> x.getStoneCount()==0 && !x.isKalah()).findAny();
		if(pit.isPresent()) {
			return false;
		}
		return true;
	}
	public Collection<Pit> getPits(){
		return this.pitIdPitMap.values();
	}

	public boolean contains(Pit pit) {
		return this.pitIdPitMap.values().contains(pit);
	}
}
