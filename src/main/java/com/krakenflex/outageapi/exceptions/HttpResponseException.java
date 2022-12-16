package com.krakenflex.outageapi.exceptions;

import com.krakenflex.outageapi.domain.HttpException;

public class HttpResponseException extends Exception {
  private final HttpException httpException;

  public HttpResponseException(HttpException httpException) {
    super(
        "API returned HTTP code "
            + httpException.statusCode()
            + " with message: "
            + httpException.message());
    this.httpException = httpException;
  }

  public HttpException getHttpException() {
    return httpException;
  }
}
