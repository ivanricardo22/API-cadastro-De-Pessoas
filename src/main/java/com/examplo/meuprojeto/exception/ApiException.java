package com.examplo.meuprojeto.exception;

public class ApiException extends RuntimeException {

  private final String code;
  private final String description;
  private final Integer statusCode;

    public ApiException(String code, String description, Integer statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;

    }

  public ApiException(String code, String description, Integer statusCode,Throwable cause) {
    super(description, cause);
    this.code = code;
    this.description = description;
    this.statusCode = statusCode;

  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public Integer getStatusCode() {
    return statusCode;
  }
}
