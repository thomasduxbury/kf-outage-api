package com.krakenflex.outageapi.domain;

import java.util.List;

public record SiteInfo(String id, String name, List<Device> devices) {}

record Device(String id, String name) {}
