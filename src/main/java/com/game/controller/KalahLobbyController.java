package com.game.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.game.entities.KalahGame;
import com.game.entities.Player;
import com.game.service.KalahService;

@RestController
@RequestMapping(path = "/games")
public class KalahLobbyController {
	@Autowired
	@Qualifier(value = "kalahService")
	private KalahService kalahService;

	@RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> joinGame(HttpSession session) {
		Player playerInfo = new Player(session.getId());
		KalahGame game = kalahService.getWaitingGame();
		kalahService.joinPlayer(game, playerInfo);
		return ResponseEntity.ok("Hello");
	}
	
	@RequestMapping(path = "/{gameId}/pits/{pitId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> makeMove(HttpSession session,@PathVariable("gameId") int gameId, 
			@PathVariable("pitId") int pitId) {
		Player playerInfo = new Player(session.getId());
		kalahService.makeMove(playerInfo,gameId,pitId);
		return ResponseEntity.ok("Hello");

	}

}
