package com.TCDZH.client.exceptions;

public class ServerErrorException extends ServiceException{

  /**
   * Constructor for Client Error Errors.
   *
   * @param message Error message to include in exception
   */
  public ServerErrorException(String message) {
    super(message);
  }
}
