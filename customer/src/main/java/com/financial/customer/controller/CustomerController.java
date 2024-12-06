package com.financial.customer.controller;

import com.financial.customer.customer.business.CustomerService;
import com.financial.customer.model.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    @Autowired
    private  CustomerService customerService;

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerByCode(String codigoUnico, ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        log.info("requestId: {}",headers.get("requestId"));

        return customerService
                .findByUserCode(codigoUnico)
                .doOnSubscribe(el -> log.info("Endpoint GET has stated"))
                .map(ResponseEntity::ok)
                .doOnSuccess(el -> log.info("Endpoint GET has completed"));
    }
}
