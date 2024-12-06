package com.demo.bff.financialinformations.dao.impl;

import com.demo.bff.financialinformations.dao.FinancialInformationDao;
import com.demo.bff.financialinformations.dao.mapper.FinancialInformationDaoMapper;
import com.demo.bff.financialinformations.model.thirdparty.CustomerInformationDaoResponse;
import com.demo.bff.financialinformations.model.thirdparty.customer.CustomerProxyResponse;
import com.demo.bff.financialinformations.proxy.CustomerProxy;
import com.demo.bff.financialinformations.proxy.ProductsProxy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class FinancialInformationDaoImpl implements FinancialInformationDao {
    @Autowired
    private  CustomerProxy customerProxy;
    @Autowired
    private  ProductsProxy productsProxy;
    @Autowired
    private  FinancialInformationDaoMapper financialInformationDaoMapper;

    @Override
    public Mono<CustomerInformationDaoResponse> getCustomerFinancialProducts(String codigoUnico, HttpHeaders headers) {
        return customerProxy.getCustomerByCode(codigoUnico,headers).
                flatMap(response -> mapProducts(response,headers));
    }

    private Mono<CustomerInformationDaoResponse> mapProducts(
            CustomerProxyResponse customerResponse, HttpHeaders headers){
        return productsProxy.getFinancialProducts(customerResponse.getNumeroDocumento(),headers)
                .map(el -> {
                    log.info("llego data cust: {}",customerResponse);
                    log.info("llego data: {}",el);
                    return financialInformationDaoMapper.toMapDao(customerResponse,el);
                });
    }
}
