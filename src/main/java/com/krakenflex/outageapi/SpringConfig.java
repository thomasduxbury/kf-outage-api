package com.krakenflex.outageapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class SpringConfig {
  @Bean
  public HttpClient httpClient() {
    return HttpClient.newBuilder().build();
  }
}
