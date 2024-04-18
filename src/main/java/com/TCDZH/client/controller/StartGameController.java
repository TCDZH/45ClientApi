package com.TCDZH.client.controller;

import com.TCDZH.api.client.controller.StartGameApi;
import org.springframework.http.ResponseEntity;

public class StartGameController implements StartGameApi {


  @Override
  public ResponseEntity<Void> startGameTrumpPost(String trump) {
    //Calls service class, which handles setting the board trump and the player scoreboard?
    return null;
  }
}
