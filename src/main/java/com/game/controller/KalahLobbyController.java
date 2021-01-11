package com.game.controller;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.dto.GameResponse;
import com.game.entities.KalahGame;
import com.game.entities.Pit;
import com.game.entities.Player;
import com.game.exception.AppControllerAdvice;
import com.game.service.KalahGameService;
import com.game.utils.KalahGameHelper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class KalahLobbyController {
	private KalahGameService kalahService;
	private final Environment environment;

	@Autowired
	public KalahLobbyController(@Qualifier(value = "kalahService") final KalahGameService kalahService,
			final Environment environment) {
		this.kalahService = kalahService;
		this.environment = environment;
	}
	@ApiOperation(value = "Create or get game",  response = GameResponse.class,
            tags = {"Games"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the game detail with url", response = GameResponse.class),
            @ApiResponse(code = 400, message = "Error Details", response = AppControllerAdvice.Reason.class)})
    @PostMapping("/games")
	public ResponseEntity<GameResponse> joinGame(HttpSession session) {
		Player playerInfo = new Player(session.getId());
		KalahGame game = kalahService.getWaitingGame();
		kalahService.joinPlayer(game, playerInfo);
		GameResponse response = new GameResponse();
		response.setId(game.getGameId());
		response.setUn(KalahGameHelper.getUrl(environment.getProperty("server.port"),game.getGameId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	 @ApiOperation(value = "Play the game with pit value", notes = "", response = GameResponse.class,
	            tags = {"Game Play"})
	 @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "Returns the game info with status", response = GameResponse.class),
	            @ApiResponse(code = 404, message = "Error Details", response = AppControllerAdvice.Reason.class),
	            @ApiResponse(code = 400, message = "Error Details", response = AppControllerAdvice.Reason.class),
	            @ApiResponse(code = 401, message = "Error Details", response = AppControllerAdvice.Reason.class)})
    @PutMapping("/games/{gameId}/pits/{pitId}")
	public ResponseEntity<GameResponse> makeMove(HttpSession session, @PathVariable("gameId") int gameId,
			@PathVariable("pitId") int pitId) {
		Player playerInfo = new Player(session.getId());
		KalahGame game = kalahService.makeMove(playerInfo, gameId, pitId);

		final Map<Integer, Integer> statusMap = game.getRoom().getGameAreaMap().values().stream()
				.flatMap(x -> x.getPits().stream())
				.collect(Collectors.toMap(Pit::getPitId, value -> value.getStoneCount()));
		GameResponse response = new GameResponse();
		response.setId(game.getGameId());
		response.setUn(KalahGameHelper.getUrl(environment.getProperty("server.port"),game.getGameId()));
		response.setStatus(statusMap);
		return ResponseEntity.ok(response);

	}

	
}
