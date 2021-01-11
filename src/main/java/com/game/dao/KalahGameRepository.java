package com.game.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.game.Enum.GameStatus;
import com.game.Enum.KalahGameType;
import com.game.entities.KalahGame;
import com.game.exception.ApplicationException;
import com.game.exception.ErrorCode;

@Component
public class KalahGameRepository {
	
	private final Map<Integer,KalahGame> games = new HashMap<>();
	public KalahGame getGame(GameStatus gameStatus) {
		Optional<KalahGame> kalha =  games.values().stream().filter(x-> x.hasGameStatus(gameStatus)).findFirst();
		if(kalha.isPresent()) {
		  return kalha.get();	
		}
		KalahGame game = KalahGame.getInstance(KalahGameType.SIX);
		games.put(game.getGameId(),game);
		return game;
	}
	public KalahGame getGame(int gameId) {
		if(!games.containsKey(gameId)) {
			throw new ApplicationException(ErrorCode.GAME_NOT_FOUND_ERROR);
		}
	  return games.get(gameId);	
	}


}
