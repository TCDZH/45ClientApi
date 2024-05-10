package com.TCDZH.client.exceptions;

public class GameNotFoundExcecption extends ServiceException{

  /**
   * Constructor for GameNotFound Errors.
   *
   * @param message Error message to include in exception
   */
  public GameNotFoundExcecption(String message) {
    super(message);
  }
}
