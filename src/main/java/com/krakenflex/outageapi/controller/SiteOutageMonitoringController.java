package com.krakenflex.outageapi.controller;

import com.krakenflex.outageapi.services.SiteOutageMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class SiteOutageMonitoringController {

  private SiteOutageMonitoringService siteOutageMonitoringService;

  public SiteOutageMonitoringController(
      @Autowired SiteOutageMonitoringService siteOutageMonitoringService) {
    this.siteOutageMonitoringService = siteOutageMonitoringService;
  }

  @PostMapping("/monitor-site/{siteId}")
  public ResponseEntity<String> postMonitorSite(
      @PathVariable String siteId, @RequestParam(required = false) String validOutageStart) {
    validOutageStart = validOutageStart != null ? validOutageStart : "2022-01-01T00:00:00.000Z";
    siteOutageMonitoringService.checkSite(siteId, Instant.parse(validOutageStart));
    return ResponseEntity.ok().build();
  }
}
