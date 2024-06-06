package com.TCDZH.client.service;

import com.TCDZH.api.client.domain.NewHand;
import com.TCDZH.api.client.domain.SuitEnum;
import com.TCDZH.client.exceptions.GameNotFoundExcecption;
import com.TCDZH.client.exceptions.ServerErrorException;
import com.TCDZH.client.exceptions.ServiceException;
import com.TCDZH.client.models.Board;
import com.TCDZH.client.models.Card;
import com.TCDZH.client.models.Player;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class ClientService {

  //Will need to have functions that map to the controllers of the service, these functions will be triggered by interactions in the UI

  //This class will contain the functions that call the UI controller to makes changes to the UI

  //Will need to have an autowired Board, Player and config class

  private Board board;

  private Player player;

  private GameConfig config;

  private String connectedGameId;

  @Value("${server.port}")
  private String clientPort;

  WebClient webClient;

  @Autowired
  public ClientService(Board board, Player player, GameConfig config) {
    this.board = board;
    this.config = config;
    this.player = player;
    this.webClient = WebClient.builder().build();
  }

  @Value("${server.port}")
  String port;

  /**
   * Just calling the function on the board that will add the card, then make a call to the UI controller to put card on board
   */
  public void addCardToBoard(Card card) {
    board.addCard(card);
    board.incrementGameTurn();
    //Call function to place the cards from the board onto the UI
  }

  /**
   * Call the UI controller to go to the end game screen (or at least plaster a big game over graphic) which includes the player No
   */
  public void endGame(Integer winPlayer) {
    //Call function on UI to switch to end screen

  }

  /**
   * Update the scoreboard on the board object, Clear the cards from the board and display which player won
   * (place something (crown?) next the card that won before it clears?), update the game turn with who won
   */
  public void endHand(Integer handWinner) {
    board.updateScoreboard(handWinner);
    board.resetBoard();
    //UI function to place an indicator next to the winning card and declare the winner of that hand
    //UI funciton to clear all the cards from the UI board
    board.setGameTurn(handWinner);

  }

  /**
   * Takes the list of new cards and adds it to the (now empty) hand, replace the old trump with the new one
   */
  public void endRound(SuitEnum trump, NewHand newHand) {
    player.setNewHand(newHand);
    board.setTrump(trump);
  }

  /**
   * Moves from the start screen to the main screen, need to setup scoreboard, recive inital hand of cards, set inital trump
   */
  public void startGame(Integer players, SuitEnum trump, NewHand newHand) {
    board.setupScoreBoard(players);
    board.setTrump(trump);
    player.setNewHand(newHand);
    //UI function to move from the wait screen to the main screen
  }

  /**
   * Triggered by a button in the UI start screen, move player to waiting screen with button to start game? need to set player number
   */
  public void createGame(String gameSize) {
    String gameId = webClient.post().uri(config.url() + "/create-game/" + gameSize)
        .header("port", clientPort)
        .accept(MediaType.APPLICATION_JSON).acceptCharset(StandardCharsets.UTF_8).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> clientResponse.toEntity(Void.class)
            .map(entity -> new ServerErrorException(entity.getStatusCode().toString())))
        .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse -> clientResponse.toEntity(Void.class)
            .map(entity -> new ServiceException(entity.getStatusCode().toString()))).bodyToMono(String.class).block();
    connectedGameId = gameId;

    //UI function to send player to waiting screen and display the gameId on the screen

  }

  /**
   * Triggered by a button in UI start screen when a game id is entered by the user, handling for unknown gameId done with REH
   * Need to set player number
   */
  public void joinGame(String gameId) {
    connectedGameId = gameId;
    String playerNo = webClient.post().uri(config.url() + "/join-game/" + gameId)
        .header("port", clientPort)
        .accept(MediaType.APPLICATION_JSON).acceptCharset(StandardCharsets.UTF_8).retrieve()
        .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.toEntity(String.class)
            .map(entity -> new GameNotFoundExcecption(entity.getStatusCode() + " " + entity.getBody())))
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> clientResponse.toEntity(Void.class)
            .map(entity -> new ServerErrorException(entity.getStatusCode().toString())))
        .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse -> clientResponse.toEntity(Void.class)
            .map(entity -> new ServiceException(entity.getStatusCode().toString()))).bodyToMono(String.class).block();

    player.setPlayerNo(Integer.parseInt(playerNo));

    //UI function to move player into waiting screen (waiting for players to join message?)

  }

  /**
   * Triggered by UI, Run the check playable methods, if card can be played, send request to server to add the card
   */
  public void playCard(Card card) {
    if(board.cardPlayable(card,player.getHand(),player.getPlayerNo())){
      webClient.post().uri(config.url() + "/add-card/" + connectedGameId)
          .header("port", clientPort)
          .accept(MediaType.APPLICATION_JSON).acceptCharset(StandardCharsets.UTF_8).retrieve()
          .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> clientResponse.toEntity(Void.class)
              .map(entity -> new ServerErrorException(entity.getStatusCode().toString())))
          .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse -> clientResponse.toEntity(Void.class)
              .map(entity -> new ServiceException(entity.getStatusCode().toString()))).bodyToMono(String.class).block();

      //UI function to remove the played card from the hand
    }else{
      //UI function to notify the player that this card is not playable.
    }

    //The above add card function will put the card onto the UI,
  }
}
