package com.krakenflex.outageapi.exceptions;

import com.krakenflex.outageapi.domain.HttpResponseDetails;

public class HttpResponseException extends Exception {
  private final HttpResponseDetails httpResponseDetails;

  public HttpResponseException(HttpResponseDetails httpResponseDetails) {
    super(
        "API returned HTTP code "
            + httpResponseDetails.statusCode()
            + " with message: "
            + httpResponseDetails.message());
    this.httpResponseDetails = httpResponseDetails;
  }

  public HttpResponseDetails getHttpException() {
    return httpResponseDetails;
  }
}
