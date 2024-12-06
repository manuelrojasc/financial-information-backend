package com.demo.bff.financialinformations.business.impl;

import com.demo.bff.financialinformations.business.FinancialInformationService;
import com.demo.bff.financialinformations.dao.impl.FinancialInformationDaoImpl;
import com.demo.bff.financialinformations.mapper.FinancialInformationServiceMapper;
import com.demo.bff.model.CustomerProductsInformationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FinancialInformationServiceImpl implements FinancialInformationService {

    private FinancialInformationDaoImpl financialInformationDaoImpl;
    private FinancialInformationServiceMapper financialInformationServiceMapper;


    @Override
    public Mono<CustomerProductsInformationResponse> getCustomerFinancialProducts(String codigoUnico, HttpHeaders headers) {
        return financialInformationDaoImpl.getCustomerFinancialProducts(codigoUnico,headers)
                .map(financialInformationServiceMapper::toMap);
    }
}
