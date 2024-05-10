package com.TCDZH.client.models;


import com.TCDZH.api.client.domain.NewHand;
import com.TCDZH.api.client.domain.ServerCard;
import java.util.ArrayList;
import lombok.Data;
import org.springframework.stereotype.Component;

//Player bean that lives in the service class, created in the service function that responds to the join game button press
@Data
@Component
public class Player {

  private int playerNo;

  private int score;

  private ArrayList<Card> hand;

  //TODO: Unit test this function, dont trust it
  public void setNewHand(NewHand newHand){
    hand.clear(); // just in case
    for (ServerCard card:  newHand){
      hand.add(new Card(card,playerNo));
    }
  }

  public Player(){
    this.score = 0;
    this.hand = new ArrayList<>();
  }



}
