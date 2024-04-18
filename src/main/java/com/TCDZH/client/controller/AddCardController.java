package com.TCDZH.client.controller;

import com.TCDZH.api.client.controller.AddCardApi;
import com.TCDZH.api.client.domain.ServerCard;
import org.springframework.http.ResponseEntity;

public class AddCardController implements AddCardApi {

  @Override
  public ResponseEntity<Void> addCardPost(ServerCard card) {
    //Convert server cars to regular Card and add player number in here
    //Service call to add card to an autowired board
    return null;
  }
}
