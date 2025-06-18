package com.examplo.cadastro.exception;


public class BadRequestException extends ApiException {

  public BadRequestException(String message) {
    super("Bad Request", message, 400);
  }

}
