package com.TCDZH.client.models;


import com.TCDZH.api.client.domain.ServerCard;
import com.TCDZH.api.client.domain.SuitEnum;
import lombok.Data;

@Data
//When the cards are received by the player, their own player number is added, power is calced when sent to the server
public class Card {

  private SuitEnum suit;

  private int number;

  private int power;

  private int player;


  public Card (ServerCard serverCard, int player){
    this.suit = serverCard.getSuit();
    this.number = serverCard.getNumber();
    this.power = serverCard.getPower();
    this.player = player;
  }

}
