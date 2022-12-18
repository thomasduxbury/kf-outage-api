package com.krakenflex.outageapi.services;

import com.krakenflex.outageapi.domain.Device;
import com.krakenflex.outageapi.domain.EnhancedOutage;
import com.krakenflex.outageapi.domain.Outage;
import com.krakenflex.outageapi.domain.SiteInfo;

import java.util.Arrays;
import java.util.List;

public class SampleTestData {
  public static final List<Outage> outages =
      Arrays.asList(
          new Outage(
              "002b28fc-283c-47ec-9af2-ea287336dc1b",
              "2021-07-26T17:09:31.036Z",
              "2021-08-29T00:37:42.253Z"),
          new Outage(
              "002b28fc-283c-47ec-9af2-ea287336dc1b",
              "2022-05-23T12:21:27.377Z",
              "2022-11-13T02:16:38.905Z"),
          new Outage(
              "002b28fc-283c-47ec-9af2-ea287336dc1b",
              "2022-12-04T09:59:33.628Z",
              "2022-12-12T22:35:13.815Z"),
          new Outage(
              "04ccad00-eb8d-4045-8994-b569cb4b64c1",
              "2022-07-12T16:31:47.254Z",
              "2022-10-13T04:05:10.044Z"),
          new Outage(
              "086b0d53-b311-4441-aaf3-935646f03d4d",
              "2022-07-12T16:31:47.254Z",
              "2022-10-13T04:05:10.044Z"),
          new Outage(
              "27820d4a-1bc4-4fc1-a5f0-bcb3627e94a1",
              "2021-07-12T16:31:47.254Z",
              "2022-10-13T04:05:10.044Z"));

  public static final List<EnhancedOutage> enhancedOutages =
      Arrays.asList(
          new EnhancedOutage(
              "002b28fc-283c-47ec-9af2-ea287336dc1b",
              "Battery 1",
              "2022-05-23T12:21:27.377Z",
              "2022-11-13T02:16:38.905Z"),
          new EnhancedOutage(
              "002b28fc-283c-47ec-9af2-ea287336dc1b",
              "Battery 1",
              "2022-12-04T09:59:33.628Z",
              "2022-12-12T22:35:13.815Z"),
          new EnhancedOutage(
              "086b0d53-b311-4441-aaf3-935646f03d4d",
              "Battery 2",
              "2022-07-12T16:31:47.254Z",
              "2022-10-13T04:05:10.044Z"));

  public static final List<Device> devices =
      Arrays.asList(
          new Device("002b28fc-283c-47ec-9af2-ea287336dc1b", "Battery 1"),
          new Device("086b0d53-b311-4441-aaf3-935646f03d4d", "Battery 2"));

  public static final SiteInfo siteInfo = new SiteInfo("kingfisher", "KingFisher", devices);
}
