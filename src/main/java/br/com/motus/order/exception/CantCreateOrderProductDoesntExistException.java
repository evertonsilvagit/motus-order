package br.com.motus.order.exception;

public class CantCreateOrderProductDoesntExistException extends RuntimeException {

  public CantCreateOrderProductDoesntExistException(String message) {
    super(message);
  }

}
