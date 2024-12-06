package com.demo.bff.financialinformations.dao;

import com.demo.bff.financialinformations.dao.impl.FinancialInformationDaoImpl;
import com.demo.bff.financialinformations.dao.mapper.FinancialInformationDaoMapper;
import com.demo.bff.financialinformations.model.thirdparty.CustomerInformationDaoResponse;
import com.demo.bff.financialinformations.model.thirdparty.customer.CustomerProxyResponse;
import com.demo.bff.financialinformations.model.thirdparty.products.FinancialProductsProxyResponse;
import com.demo.bff.financialinformations.proxy.CustomerProxy;
import com.demo.bff.financialinformations.proxy.ProductsProxy;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.demo.bff.TestUtil.buildHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinancialInformationDaoImplTest {
    @Mock
    private CustomerProxy customerProxy;
    @Mock
    private ProductsProxy productsProxy;
    @Mock
    private FinancialInformationDaoMapper financialInformationDaoMapper;
    @InjectMocks
    private FinancialInformationDaoImpl financialInformationDaoImpl;

    @Test
    @DisplayName("return Success when get financial information dao")
    void returnSuccessWhenGetFinancialInformationDao() {
        CustomerInformationDaoResponse mockResponse = new CustomerInformationDaoResponse();

        when(customerProxy.getCustomerByCode(anyString(),any()))
                .thenReturn(Mono.just(buildCustomer()));
        when(productsProxy.getFinancialProducts(anyString(),any()))
                .thenReturn(Mono.just(new FinancialProductsProxyResponse()));
        when(financialInformationDaoMapper.toMapDao(any(),any()))
                .thenReturn(mockResponse);

        StepVerifier.create(financialInformationDaoImpl.getCustomerFinancialProducts("12345",buildHeaders()))
                .expectNext(mockResponse)
                .verifyComplete();
    }

    @Test
    @DisplayName("return Empty when get financial information dao")
    void returnEmptyWhenGetFinancialInformationDao() {
        lenient().when(customerProxy.getCustomerByCode(anyString(),any()))
                .thenReturn(Mono.empty());
        lenient().when(productsProxy.getFinancialProducts(anyString(),any()))
                .thenReturn(Mono.empty());
        lenient().when(financialInformationDaoMapper.toMapDao(any(),any()))
                .thenReturn(new CustomerInformationDaoResponse());

        StepVerifier.create(financialInformationDaoImpl
                        .getCustomerFinancialProducts(StringUtils.EMPTY,buildHeaders()))
                .verifyComplete();
    }

    @Test
    @DisplayName("return error when get financial information dao")
    void returnErrorWhenGetFinancialInformationDao() {
        when(customerProxy.getCustomerByCode(anyString(),any()))
                .thenReturn(Mono.error(new RuntimeException("error")));

        StepVerifier.create(financialInformationDaoImpl
                        .getCustomerFinancialProducts(StringUtils.EMPTY,buildHeaders()))
                .expectError(RuntimeException.class)
                .verify();
    }

    private CustomerProxyResponse buildCustomer(){
        return CustomerProxyResponse.builder()
                .nombre("marc")
                .tipoDocumento("1")
                .numeroDocumento("12345678")
                .apellidos("zz dd")
                .build();
    }

}
