package com.demo.bff.financialinformations.dao;

import com.demo.bff.financialinformations.model.thirdparty.CustomerInformationDaoResponse;

import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

public interface FinancialInformationDao {
    Mono<CustomerInformationDaoResponse> getCustomerFinancialProducts(String codigoUnico, HttpHeaders headers);
}
