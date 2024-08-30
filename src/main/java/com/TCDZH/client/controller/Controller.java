package com.TCDZH.client.controller;

import com.TCDZH.api.client.controller.AddCardApi;
import com.TCDZH.api.client.controller.EndGameApi;
import com.TCDZH.api.client.controller.EndHandApi;
import com.TCDZH.api.client.controller.EndRoundApi;
import com.TCDZH.api.client.controller.StartGameApi;
import com.TCDZH.api.client.domain.NewHand;
import com.TCDZH.api.client.domain.ServerCard;
import com.TCDZH.api.client.domain.SuitEnum;
import com.TCDZH.client.UI.EndScreenController;
import com.TCDZH.client.UI.MainScreenController;
import com.TCDZH.client.UI.StartScreenController;
import com.TCDZH.client.UI.WaitScreenController;
import com.TCDZH.client.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller implements AddCardApi, EndGameApi, EndHandApi, EndRoundApi, StartGameApi{

  private MainScreenController mainScreenController;

  private StartScreenController startScreenController;

  //TODO: Need to have an endpoint for starting the game
  private WaitScreenController waitScreenController;


  @Autowired
  public Controller (MainScreenController mainScreenController, StartScreenController startScreenController,
  WaitScreenController waitScreenController, EndScreenController endScreenController){
    this.mainScreenController = mainScreenController;
    this.startScreenController = startScreenController;
    this.waitScreenController = waitScreenController;
  }

  @Override
  public ResponseEntity<Void> addCardPost(ServerCard serverCard) {
    //Convert server cards to regular Card and add player number in here
    Card card = new Card(serverCard);
    mainScreenController.addCard(card);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> endGamePost(Integer playerNo) {
    mainScreenController.endGame(playerNo);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> endHandPost(Integer playerNo) {
    mainScreenController.endHand(playerNo);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> endRoundPost(String trump, NewHand newHand) {
    SuitEnum trumpEnum = SuitEnum.valueOf(trump);
    mainScreenController.endRound(trumpEnum,newHand);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> startGameTrumpPlayerCountPost(String trump, Integer playerCount, NewHand newHand) {
    SuitEnum trumpEnum = SuitEnum.valueOf(trump);
    System.out.println(trump + playerCount + newHand);
    waitScreenController.startGame(trumpEnum,playerCount,newHand);
    mainScreenController.refreshCardDisplay();
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
