package com.krakenflex.outageapi.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.krakenflex.outageapi.domain.Outage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OutageRepositoryRestImpl implements OutageRepository {
  private final String apiUrl;
  private final String apiKey;
  private final HttpClient httpClient;
  private final Type listType = new TypeToken<ArrayList<Outage>>() {}.getType();
  Logger log = LoggerFactory.getLogger(OutageRepositoryRestImpl.class);

  OutageRepositoryRestImpl(
      @Value("${api.url}") String apiUrl,
      @Value("${api.key}") String apiKey,
      @Autowired HttpClient httpClient) {
    this.apiKey = apiKey;
    this.apiUrl = apiUrl;
    this.httpClient = httpClient;
  }

  @Override
  public List<Outage> getOutages() {
    log.info("Retrieving all outages from {}", apiUrl);
    HttpRequest req =
        HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(apiUrl + "/outages"))
            .header("x-api-key", apiKey)
            .build();
    HttpResponse<String> res;
    try {
      res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    if (res.statusCode() == 200) {
      return new Gson().fromJson(res.body(), listType);
    } else {
      return new ArrayList<>();
    }
  }
}
