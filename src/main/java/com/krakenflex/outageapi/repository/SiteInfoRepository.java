package com.krakenflex.outageapi.repository;

import com.krakenflex.outageapi.domain.SiteInfo;

import java.util.Optional;

public interface SiteInfoRepository {
  Optional<SiteInfo> getSiteInfo(String siteId);
}
