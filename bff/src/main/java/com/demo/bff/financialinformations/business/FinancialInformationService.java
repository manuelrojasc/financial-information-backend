package com.demo.bff.financialinformations.business;

import com.demo.bff.model.CustomerProductsInformationResponse;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

public interface FinancialInformationService {
    Mono<CustomerProductsInformationResponse> getCustomerFinancialProducts(String codigoUnico, HttpHeaders headers);
}
