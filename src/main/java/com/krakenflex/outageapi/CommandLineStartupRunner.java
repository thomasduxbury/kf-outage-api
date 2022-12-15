package com.krakenflex.outageapi;

import com.krakenflex.outageapi.domain.Outage;
import com.krakenflex.outageapi.domain.SiteInfo;
import com.krakenflex.outageapi.repository.OutageRepository;
import com.krakenflex.outageapi.repository.SiteInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineStartupRunner implements CommandLineRunner {
  @Autowired private OutageRepository outageRepository;
  @Autowired private SiteInfoRepository siteInfoRepository;

  @Override
  public void run(String... args) throws Exception {
    List<Outage> outages = outageRepository.getOutages();
    outages.stream().forEach(System.out::println);
    SiteInfo siteInfo = siteInfoRepository.getSiteInfo("norwich-pear-tree").get();
    System.out.println(siteInfo);
  }
}
