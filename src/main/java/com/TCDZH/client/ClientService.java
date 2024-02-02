package com.TCDZH.client;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class ClientService {

  @Value("${server.port}")
  String port;

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
