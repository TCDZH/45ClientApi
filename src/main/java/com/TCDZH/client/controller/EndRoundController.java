package com.TCDZH.client.controller;

import com.TCDZH.api.client.controller.EndRoundApi;
import com.TCDZH.api.client.domain.NewHand;
import org.springframework.http.ResponseEntity;

public class EndRoundController implements EndRoundApi {

  @Override
  public ResponseEntity<Void> endRoundTrumpPost(String trump, NewHand newHand) {
    //Gives the players 5 new cards to add to their hand, translate this into a suit enum before passing to service
    return null;
  }
}
