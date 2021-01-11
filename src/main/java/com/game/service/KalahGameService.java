package com.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.Enum.GameStatus;
import com.game.context.KalahContext;
import com.game.dao.KalahGameRepository;
import com.game.entities.KalahGame;
import com.game.entities.Player;

/** Business layer for Kalah Game
 * @author indiv
 *
 */
@Service("kalahService")
public class KalahGameService {

	@Autowired
	private KalahGameRepository repository;

	/** Creates game or get waiting game
	 * @return
	 */
	public KalahGame createOrGetWaitingGame() {
		KalahGame game = repository.createOrGetWaitingGame(GameStatus.WAITING);
		return game;
	}
	
	/** If player joins kalah game
	 * @param game
	 * @param playerInfo
	 * @return
	 */
	public KalahGame joinPlayer(KalahGame game,Player playerInfo) {
		return game.joinPlayer(playerInfo);
	}

	/** If player makes a move
	 * @param playerInfo
	 * @param gameId
	 * @param pitId
	 * @return
	 */
	public KalahGame makeMove(Player playerInfo, int gameId, int pitId) {
		KalahGame game = repository.getGame(gameId);
		KalahContext.setTurn(playerInfo);

		return game.makeMove(pitId);

	}

}
