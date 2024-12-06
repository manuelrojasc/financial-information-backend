package com.financial.products.controller;

import com.financial.product.controller.FinancialProductsApi;
import com.financial.product.model.FinancialProductsResponse;
import com.financial.products.products.business.FinancialProductsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class FinancialProductsController implements FinancialProductsApi {

    @Autowired
    private FinancialProductsService financialProductsService;

    @Override
    public Mono<ResponseEntity<FinancialProductsResponse>> getFinancialProducts(String cic, ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        log.info("requestId: {}",headers.get("requestId"));

        return financialProductsService.findByUserCode(cic)
                .doOnSubscribe(el -> log.info("Endpoint GET has stated"))
                .map(ResponseEntity::ok)
                .doOnSuccess(el -> log.info("Endpoint GET has completed"));
    }
}
