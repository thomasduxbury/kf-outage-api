package com.krakenflex.outageapi.services;

import com.krakenflex.outageapi.domain.Device;
import com.krakenflex.outageapi.domain.EnhancedOutage;
import com.krakenflex.outageapi.domain.Outage;
import com.krakenflex.outageapi.domain.SiteInfo;
import com.krakenflex.outageapi.exceptions.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class SiteOutageMonitoringService {
  Logger log = LoggerFactory.getLogger(SiteOutageMonitoringService.class);
  private SiteOutageHttpService siteOutageHttpService;

  public SiteOutageMonitoringService(@Autowired SiteOutageHttpService siteOutageHttpService) {
    this.siteOutageHttpService = siteOutageHttpService;
  }

  public void checkSite(String siteId, Instant validOutageStart) {
    List<Outage> outages;
    try {
      outages = siteOutageHttpService.getOutages();
    } catch (HttpResponseException e) {
      if (e.getHttpException().statusCode() == 500) {
        System.out.println("Error 500 occurred, deal with as required");
      } else {
        log.warn(e.getMessage());
      }
      return;
    }

    SiteInfo siteInfo;
    try {
      siteInfo = siteOutageHttpService.getSiteInfo(siteId).orElseThrow();
    } catch (HttpResponseException e) {
      if (e.getHttpException().statusCode() == 500) {
        System.out.println("Error 500 occurred, deal with as required");
      } else {
        log.warn(e.getMessage());
      }
      return;
    }

    List<EnhancedOutage> enhancedOutages = new ArrayList<>();
    for (Device device : siteInfo.devices()) {
      for (Outage outage : outages) {
        if (outage.id().equals(device.id())
            && isValidOutageBegin(Instant.parse(outage.begin()), validOutageStart)) {
          enhancedOutages.add(
              new EnhancedOutage(outage.id(), device.name(), outage.begin(), outage.end()));
        }
      }
    }

    for (EnhancedOutage enhancedOutage : enhancedOutages) {
      System.out.println(enhancedOutage);
    }
    try {
      siteOutageHttpService.postSiteOutages(siteId, enhancedOutages);
      log.info("Successfully posted enhanced outage info");
    } catch (HttpResponseException e) {
      if (e.getHttpException().statusCode() == 500) {
        System.out.println("Error 500 occurred, deal with as required");
      } else {
        log.warn(e.getMessage());
      }
    }
  }

  private boolean isValidOutageBegin(Instant begin, Instant validOutageStart) {
    return !begin.isBefore(validOutageStart);
  }
}
