package com.TCDZH.client.service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class ClientService {

  //Will need to have functions that map to the controllers of the service, these functions will be triggered by interactions in the UI


  //This class will contain the functions that call the UI controller to makes changes to the UI

  @Value("${server.port}")
  String port;


  //function not going to be in final thing, just leaving in for shits and references
  public void initialCallServer(){

    final WebClient client = WebClient.builder()
        .build();



    final ResponseEntity<String> response = client.get().uri("http://localhost:8081/45Server")
        .header("ClientPort",port)
        .accept(APPLICATION_JSON)
        .acceptCharset(UTF_8)
        .retrieve()
        .toEntity(String.class)
        .block();

    System.out.println(response.getBody());
  }


}
