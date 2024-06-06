package com.TCDZH.client.models;

import com.TCDZH.api.client.domain.SuitEnum;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Board {

  private ArrayList<Card> pile;

  //Needed for calculation on if the card can be played or not, sent by start game and end round function
  private SuitEnum trump;

  //TODO: in the function that adds the card to the board, need logic for led card, need some unit tests for this
  private SuitEnum ledCard;

  //TODO: working soloution is to send the number of joined players in the start game function, iterates through to that number adding items to the map with a score of 0
  //when hands are won the scoreboard is updated using the player number, have functionality for user names? probs not
  private HashMap<String, Integer> scoreBoard;

  //Whose turn it is / who can play a card, have the logic for game turn in the card playable function?
  private int gameTurn;

  private int totalPlayers;

  public void resetBoard() {
    pile.clear();
  }

  public void setupScoreBoard(int maxPlayers) {
    totalPlayers = maxPlayers;
    for (int i = 0; i < maxPlayers; i++) {
      scoreBoard.put(Integer.toString(i), 0);
    }
  }

  public void incrementGameTurn(){
    gameTurn += 1;
    if (gameTurn == totalPlayers){
      gameTurn = 0;
    }
  }


  public void updateScoreboard(int player) {
    int currentScore = scoreBoard.get(Integer.toString(player));
    scoreBoard.put(Integer.toString(player), currentScore + 5);
  }


  public boolean cardPlayable(Card card, ArrayList<Card> playerHand, int playerNumber) {
    boolean playable = false;
    if (playerNumber == gameTurn) {
      if (card.getSuit() == trump || card.getSuit() == ledCard) {
        playable = true;
      } else {
        //Iterate through the cards in the players hand, if there is no cards that are led then it is playable
        playable = playerHand.stream().noneMatch(card1 -> card1.getSuit() == ledCard);
      }
    }
    return playable;
  }

  public void addCard(Card card) {
    //Make some call to the UI in here? Clear the board and all cards back onto it? have this in the service class?
    if (pile.isEmpty()) {
      ledCard = card.getSuit();
    }
    pile.add(card);
  }
}
