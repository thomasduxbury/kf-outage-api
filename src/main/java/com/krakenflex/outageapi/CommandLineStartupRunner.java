package com.krakenflex.outageapi;

import com.krakenflex.outageapi.services.SiteOutageMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommandLineStartupRunner implements CommandLineRunner {
  @Autowired private SiteOutageMonitoringService siteOutageMonitoringService;

  @Override
  public void run(String... args) {
    siteOutageMonitoringService.checkSite(
        "norwich-pear-tree", Instant.parse("2022-01-01T00:00:00.000Z"));
  }
}
