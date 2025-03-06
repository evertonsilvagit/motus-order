package br.com.motus.order.exception;

public class CantDeleteProductException extends RuntimeException {

  public CantDeleteProductException(String message) {
    super(message);
  }

}
