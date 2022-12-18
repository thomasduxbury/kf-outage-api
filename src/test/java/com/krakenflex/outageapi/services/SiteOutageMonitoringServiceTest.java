package com.krakenflex.outageapi.services;

import com.krakenflex.outageapi.exceptions.HttpResponseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Optional;

import static com.krakenflex.outageapi.services.SampleTestData.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SiteOutageMonitoringServiceTest {

  private SiteOutageHttpService siteOutageHttpService = Mockito.mock(SiteOutageHttpService.class);
  private SiteOutageMonitoringService siteOutageMonitoringService =
      Mockito.spy(new SiteOutageMonitoringService(siteOutageHttpService));

  @Test
  @DisplayName(
      "When checking a site, site info device ids should be matched from a list of outages, filtered based on being a 2022 outage, and sent to the enhanced outage http service method")
  public void
      given_ValidSite_when_CheckingForSiteOutages_then_EnhancedOutagesShouldBeFoundAndPostedToAPI()
          throws HttpResponseException {
    when(siteOutageHttpService.getSiteInfo("kingfisher")).thenReturn(Optional.of(siteInfo));
    when(siteOutageHttpService.getOutages()).thenReturn(outages);
    siteOutageMonitoringService.checkSite("kingfisher", Instant.parse("2022-01-01T00:00:00.000Z"));
    verify(siteOutageHttpService, times(1)).getOutages();
    verify(siteOutageHttpService, times(1)).getSiteInfo(eq("kingfisher"));
    verify(siteOutageMonitoringService, times(1))
        .filterValidSitesIntoEnhancedOutage(
            siteInfo, outages, Instant.parse("2022-01-01T00:00:00.000Z"));
    verify(siteOutageHttpService, times(1)).postSiteOutages(eq("kingfisher"), eq(enhancedOutages));
  }
}
