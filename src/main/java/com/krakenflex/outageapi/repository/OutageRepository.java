package com.krakenflex.outageapi.repository;

import com.krakenflex.outageapi.domain.Outage;

import java.util.List;

public interface OutageRepository {
  List<Outage> getOutages();
}
