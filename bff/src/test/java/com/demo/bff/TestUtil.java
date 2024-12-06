package com.demo.bff;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;


public class TestUtil {

    public static MockServerWebExchange buildServerWebExchange () {
        MockServerHttpRequest serverHttpRequest = MockServerHttpRequest
                .get("/api/v1/")
                .header("requestId","77392336-0e48-4244-9a44-7ed43bcd4647")
                .header("Content-Type","application/json")
                .header("requestDate","2020-11-16T17:15:20.509-0400")
                .build();
        return MockServerWebExchange.builder(serverHttpRequest).build();
    }

    public static HttpHeaders buildHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("requestId","77392336-0e48-4244-9a44-7ed43bcd4647");
        httpHeaders.add("Content-Type","application/json");
        httpHeaders.add("requestDate","2020-11-16T17:15:20.509-0400");
        return httpHeaders;
    }
}
