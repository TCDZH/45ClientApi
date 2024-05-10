package com.TCDZH.client.controller;

import com.TCDZH.client.exceptions.GameNotFoundExcecption;
import com.TCDZH.client.exceptions.ServerErrorException;
import com.TCDZH.client.exceptions.ServiceException;
import com.TCDZH.client.service.GameConfig;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * I feel like there should be more in here but can't think of it at the min, will have to be more of a trial and error type deal
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException exception, final HttpHeaders headers,
      final HttpStatusCode status, final WebRequest request) {
    return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(GameNotFoundExcecption.class)
  public void handleInvalidGameIdException(GameNotFoundExcecption exception){
    //TODO: UI method to display on the screen that the game Id is invalid / not found
  }

  @ResponseBody
  @ExceptionHandler(ServerErrorException.class)
  public ResponseEntity<String> handleClientErorr(ServerErrorException exception){
    return new ResponseEntity<>(exception.getMessage() + " Broadcast failed", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleServiceError(ServiceException exception){
    return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
  }
}