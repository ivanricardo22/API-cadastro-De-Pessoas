package com.examplo.meuprojeto.exception;


public class BadRequestException extends ApiException {

  public BadRequestException(String message) {
    super("Bad Request", message, 400);
  }

}
