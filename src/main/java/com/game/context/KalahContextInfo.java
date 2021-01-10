package com.game.context;

import com.game.entities.Player;

class KalahContextInfo {
	private Player turnPlayer;


	public void setTurnPlayer(Player player) {
		this.turnPlayer = player;
	}
	
	public Player getTurnPlayer() {
		return  this.turnPlayer;
	}
}
