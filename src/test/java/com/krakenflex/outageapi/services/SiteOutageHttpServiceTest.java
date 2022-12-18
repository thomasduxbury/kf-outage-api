package com.krakenflex.outageapi.services;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.krakenflex.outageapi.domain.Outage;
import com.krakenflex.outageapi.domain.SiteInfo;
import com.krakenflex.outageapi.exceptions.HttpResponseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.krakenflex.outageapi.services.SampleTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8090)
public class SiteOutageHttpServiceTest {

  private HttpClient httpClient = HttpClient.newBuilder().build();
  private String apiUrl = "http://localhost:8090";
  private String apiKey = "";
  private SiteOutageHttpService siteOutageHttpService =
      new SiteOutageHttpService(apiUrl, apiKey, httpClient);

  @Test
  @DisplayName(
      "When querying for outages via the API, the result should be returned as a List of Outages")
  void given_OutagesExist_when_GettingOutages_then_OutagesShouldBeReturned()
      throws HttpResponseException {
    stubFor(
        get("/outages")
            .willReturn(
                okJson(
                    """
        [
          {
            "id": "002b28fc-283c-47ec-9af2-ea287336dc1b",
            "begin": "2021-07-26T17:09:31.036Z",
            "end": "2021-08-29T00:37:42.253Z"
          },
          {
            "id": "002b28fc-283c-47ec-9af2-ea287336dc1b",
            "begin": "2022-05-23T12:21:27.377Z",
            "end": "2022-11-13T02:16:38.905Z"
          },
          {
            "id": "002b28fc-283c-47ec-9af2-ea287336dc1b",
            "begin": "2022-12-04T09:59:33.628Z",
            "end": "2022-12-12T22:35:13.815Z"
          },
          {
            "id": "04ccad00-eb8d-4045-8994-b569cb4b64c1",
            "begin": "2022-07-12T16:31:47.254Z",
            "end": "2022-10-13T04:05:10.044Z"
          },
          {
            "id": "086b0d53-b311-4441-aaf3-935646f03d4d",
            "begin": "2022-07-12T16:31:47.254Z",
            "end": "2022-10-13T04:05:10.044Z"
          },
          {
            "id": "27820d4a-1bc4-4fc1-a5f0-bcb3627e94a1",
            "begin": "2021-07-12T16:31:47.254Z",
            "end": "2022-10-13T04:05:10.044Z"
          }
        ]""")));

    List<Outage> actualOutages = siteOutageHttpService.getOutages();
    assertEquals(outages, actualOutages);
  }

  @Test
  @DisplayName("When querying for Site Info via the API, Site Info should be returned")
  void given_SiteInfoExists_when_RetrievingSiteInfo_then_SiteInfoShouldBeReturned()
      throws HttpResponseException {
    stubFor(
        get("/site-info/kingfisher")
            .willReturn(
                okJson(
                    """
                    {
                      "id": "kingfisher",
                      "name": "KingFisher",
                      "devices": [
                        {
                          "id": "002b28fc-283c-47ec-9af2-ea287336dc1b",
                          "name": "Battery 1"
                        },
                        {
                          "id": "086b0d53-b311-4441-aaf3-935646f03d4d",
                          "name": "Battery 2"
                        }
                      ]
                    }""")));
    SiteInfo actualSiteInfo = siteOutageHttpService.getSiteInfo("kingfisher").orElseThrow();
    assertEquals(siteInfo, actualSiteInfo);
  }

  @Test
  @DisplayName(
      "When posting to the enhanced outage info API, a list of EnhancedOutage should be serialised into JSON and attached to the request payload")
  public void given_EnhancedOutageInfo_when_PostingToSiteOutages_then_JsonPayloadShouldBeGenerated()
      throws HttpResponseException {
    stubFor(
        post("/site-outages/kingfisher")
            .withRequestBody(
                equalToJson(
                    "[{\"id\":\"002b28fc-283c-47ec-9af2-ea287336dc1b\",\"name\":\"Battery 1\",\"begin\":\"2022-05-23T12:21:27.377Z\",\"end\":\"2022-11-13T02:16:38.905Z\"},{\"id\":\"002b28fc-283c-47ec-9af2-ea287336dc1b\",\"name\":\"Battery 1\",\"begin\":\"2022-12-04T09:59:33.628Z\",\"end\":\"2022-12-12T22:35:13.815Z\"},{\"id\":\"086b0d53-b311-4441-aaf3-935646f03d4d\",\"name\":\"Battery 2\",\"begin\":\"2022-07-12T16:31:47.254Z\",\"end\":\"2022-10-13T04:05:10.044Z\"}]"))
            .willReturn(ok()));
    siteOutageHttpService.postSiteOutages("kingfisher", enhancedOutages);
  }
}
