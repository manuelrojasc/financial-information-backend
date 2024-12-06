package com.demo.bff.financialinformations.proxy;

import com.demo.bff.financialinformations.model.thirdparty.customer.CustomerProxyResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerProxy {
    @Autowired
    private WebClient webClient;

    @Value("${microservice.customer.base-url}")
    private String baseUrl;


    public Mono<CustomerProxyResponse> getCustomerByCode(String codigoUnico, HttpHeaders headers) {
        String url = baseUrl.concat(codigoUnico);

        log.info("Calling Customer Service at URL: {}", url);
        log.info("Calling Customer Service at URL with headers: {}", headers);

        return webClient
                .get()
                .uri(url)
                .headers(headersApi -> headersApi.addAll(headers))
                .retrieve()
                .bodyToMono(CustomerProxyResponse.class)
                .map(el ->{
                    log.info("llego data: {}",el);
                    return el;
                })
                .doOnSuccess(response -> log.info("Received response from Product Service: {}", response))
                .doOnError(this::handleError);
    }



    private void handleError(Throwable error) {
        if (error instanceof WebClientResponseException ex) {
            log.error("Error response: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString());
        } else {
            log.error("Unexpected error occurred", error);
        }
    }
}
