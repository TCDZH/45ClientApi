package com.TCDZH.client.controller;


import com.TCDZH.client.UI.SimpleUiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/45Client")
public class SpringControllerLegacy {

  @Autowired
  private SimpleUiController controller;

  @GetMapping
  public ResponseEntity<String> changeButton(@RequestHeader(value = "headerText",required = true)String headerText,
      @RequestHeader(value = "headerCol",required = true)String headerCol,
      WebRequest request){

    System.out.println(request.getContextPath());

    System.out.println(request.getDescription(true));

    System.out.println(headerText);
    controller.setButtonText(headerText,headerCol);

    return new ResponseEntity<>(headerText, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> postStringTest(@RequestBody String test){

    System.out.println(test);

    return new ResponseEntity<>("This worked ", HttpStatus.OK);
  }


}
