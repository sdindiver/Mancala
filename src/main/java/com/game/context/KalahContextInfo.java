package com.game.context;

import com.game.entities.Player;
import com.game.entities.PlayerArea;

class KalahContextInfo {
	private Player turnPlayer;
	
	private PlayerArea turnPlayerArea;

	public void setTurnPlayer(Player player) {
		this.turnPlayer = player;
	}
	
	public Player getTurnPlayer() {
		return  this.turnPlayer;
	}

	public PlayerArea getTurnPlayerArea() {
		return turnPlayerArea;
	}

	public void setTurnPlayerArea(PlayerArea turnPlayerArea) {
		this.turnPlayerArea = turnPlayerArea;
	}
}
