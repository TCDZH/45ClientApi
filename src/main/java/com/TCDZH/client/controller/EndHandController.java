package com.TCDZH.client.controller;

import com.TCDZH.api.client.controller.EndHandApi;
import org.springframework.http.ResponseEntity;

public class EndHandController implements EndHandApi {

  @Override
  public ResponseEntity<Void> endHandPlayerNoPost(Integer playerNo) {
    //Service call to update the scoreboard to notify the player who as won the hand/trick, also clear the player board
    // and reset/increment the turn attribute so the next player can make a turn
    return null;
  }
}
