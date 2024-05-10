package com.TCDZH.client.controller;

import com.TCDZH.api.client.controller.StartGameApi;
import com.TCDZH.api.client.domain.NewHand;
import org.springframework.http.ResponseEntity;

public class StartGameController implements StartGameApi {

  @Override
  public ResponseEntity<Void> startGameTrumpPlayerCountPost(String trump, Integer playerCount, NewHand newHand) {
    //Calls service class, which handles setting the board trump and the player scoreboard?
    return null;
  }
}
