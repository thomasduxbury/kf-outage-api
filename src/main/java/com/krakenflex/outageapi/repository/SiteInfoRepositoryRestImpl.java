package com.krakenflex.outageapi.repository;

import com.google.gson.Gson;
import com.krakenflex.outageapi.domain.SiteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Repository
public class SiteInfoRepositoryRestImpl implements SiteInfoRepository {
  private final String apiUrl;
  private final String apiKey;
  private final HttpClient httpClient;
  Logger log = LoggerFactory.getLogger(OutageRepositoryRestImpl.class);

  SiteInfoRepositoryRestImpl(
      @Value("${api.url}") String apiUrl,
      @Value("${api.key}") String apiKey,
      @Autowired HttpClient httpClient) {
    this.apiKey = apiKey;
    this.apiUrl = apiUrl;
    this.httpClient = httpClient;
  }

  @Override
  public Optional<SiteInfo> getSiteInfo(String siteId) {
    log.info("Retrieving all Site Info for {}", siteId);
    HttpRequest req =
        HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(apiUrl + "/site-info/" + siteId))
            .header("x-api-key", apiKey)
            .build();
    HttpResponse<String> res;
    try {
      res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    if (res.statusCode() == 200) {
      return Optional.ofNullable(new Gson().fromJson(res.body(), SiteInfo.class));
    } else {
      return Optional.empty();
    }
  }
}
