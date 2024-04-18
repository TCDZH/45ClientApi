package com.TCDZH.client.controller;

import com.TCDZH.api.client.controller.EndGameApi;
import org.springframework.http.ResponseEntity;

public class EndGameController implements EndGameApi {

  @Override
  public ResponseEntity<Void> endGamePlayerNoPost(Integer playerNo) {
    //Calls method in service to display the end game screen
    return null;
  }
}
