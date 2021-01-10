package com.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.Enum.GameStatus;
import com.game.context.KalahContext;
import com.game.dao.KalahGameRepository;
import com.game.entities.KalahGame;
import com.game.entities.Player;

@Service("kalahService")
public class KalahService {

	@Autowired
	private KalahGameRepository repository;

	public KalahGame getWaitingGame() {
		KalahGame game = repository.getGame(GameStatus.WAITING);
		return game;
	}
	
	public KalahGame joinPlayer(KalahGame game,Player playerInfo) {
		return game.joinPlayer(playerInfo);
	}

	public KalahGame makeMove(Player playerInfo, int gameId, int pitId) {
		KalahGame game = repository.getGame(gameId);
		KalahContext.setTurn(playerInfo);

		return game.makeMove(pitId);

	}

}
