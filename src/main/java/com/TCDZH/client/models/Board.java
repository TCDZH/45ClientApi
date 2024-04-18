package com.TCDZH.client.models;

import com.TCDZH.api.client.domain.SuitEnum;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Data;

@Data
public class Board {

  private ArrayList<Card> pile;

  //Needed for calculation on if the card can be played or not, sent by start game and end round function
  private SuitEnum trump;

  //TODO: in the function that adds the card to the board, need logic for led card, need some unit tests for this
  private SuitEnum ledCard;

  //TODO: working soloution is to send the number of joined players in the start game function, iterates through to that number adding items to the map with a score of 0
  //when hands are won the scoreboard is updated using the player number, have functionality for user names? probs not
  private HashMap<String,Integer> scoreBoard;

  public void setupScoreBoard(int maxPlayers){
    for (int i = 0; i < maxPlayers; i++) {
      scoreBoard.put(Integer.toString(i),0);
    }
  }

  public boolean cardPlayable(Card card, ArrayList<Card> playerHand){
    boolean playable;
    if (card.getSuit() == trump || card.getSuit() == ledCard){
      playable = true;
    }else{
      //Iterate through the cards in the players hand, if there is no cards that are led or trump then it is playable
      playable = playerHand.stream().noneMatch(card1 -> card1.getSuit() == trump || card1.getSuit() == ledCard);
    }

    return playable;
  }
}
