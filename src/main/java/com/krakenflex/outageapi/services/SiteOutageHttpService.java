package com.krakenflex.outageapi.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.krakenflex.outageapi.domain.EnhancedOutage;
import com.krakenflex.outageapi.domain.HttpResponseDetails;
import com.krakenflex.outageapi.domain.Outage;
import com.krakenflex.outageapi.domain.SiteInfo;
import com.krakenflex.outageapi.exceptions.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SiteOutageHttpService {
  private final String apiUrl;
  private final String apiKey;
  private final HttpClient httpClient;
  private final Type listType = new TypeToken<ArrayList<Outage>>() {}.getType();
  Logger log = LoggerFactory.getLogger(SiteOutageHttpService.class);

  SiteOutageHttpService(
      @Value("${api.url}") String apiUrl,
      @Value("${api.key}") String apiKey,
      @Autowired HttpClient httpClient) {
    this.apiKey = apiKey;
    this.apiUrl = apiUrl;
    this.httpClient = httpClient;
  }

  public List<Outage> getOutages() throws HttpResponseException {
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
      throw new HttpResponseException(new HttpResponseDetails(res.statusCode(), res.body()));
    }
  }

  public Optional<SiteInfo> getSiteInfo(String siteId) throws HttpResponseException {
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
      throw new HttpResponseException(new HttpResponseDetails(res.statusCode(), res.body()));
    }
  }

  public void postSiteOutages(String siteId, List<EnhancedOutage> enhancedOutages)
      throws HttpResponseException {
    log.info("Posting Sites Outages for site {}", siteId);
    String json = new Gson().toJson(enhancedOutages);
    HttpRequest req =
        HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .uri(URI.create(apiUrl + "/site-outages/" + siteId))
            .header("x-api-key", apiKey)
            .build();
    try {
      HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
      if (res.statusCode() != 200) {
        throw new HttpResponseException(new HttpResponseDetails(res.statusCode(), res.body()));
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
