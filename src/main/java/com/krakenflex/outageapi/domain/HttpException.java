package com.krakenflex.outageapi.domain;

public record HttpException(int statusCode, String message) {}
