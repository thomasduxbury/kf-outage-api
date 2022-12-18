package com.krakenflex.outageapi.exceptions;

import com.krakenflex.outageapi.domain.HttpException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseExceptionTest {

  @Test
  @DisplayName(
      "Exception should contain a HttpException and a message explaining what the response code and response message was")
  void shouldContainHttpExceptionWithRelevantMessage() {
    HttpException httpException = new HttpException(200, "Success!");
    HttpResponseException exception = new HttpResponseException(httpException);
    assertEquals("API returned HTTP code 200 with message: Success!", exception.getMessage());
  }
}
