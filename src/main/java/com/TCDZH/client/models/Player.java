package com.TCDZH.client.models;


import java.util.ArrayList;
import java.util.HashMap;
import lombok.Data;

//Player bean that lives in the service class, created in the service function that responds to the join game button press
@Data
public class Player {

  private int playerNo;

  private int score;

  private ArrayList<Card> hand;

  private int gameTurn;

  public Player(int playerNo){
    this.playerNo = playerNo;
    this.score = 0;
    this.hand = new ArrayList<>();
    this.gameTurn = 0;
  }



}
