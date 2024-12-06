package com.demo.bff.financialinformations.business;

import com.demo.bff.financialinformations.business.impl.FinancialInformationServiceImpl;
import com.demo.bff.financialinformations.dao.impl.FinancialInformationDaoImpl;
import com.demo.bff.financialinformations.mapper.FinancialInformationServiceMapper;
import com.demo.bff.financialinformations.model.thirdparty.CustomerInformationDaoResponse;
import com.demo.bff.model.CustomerProductsInformationResponse;
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
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class FinancialInformationServiceImplTest {
    @Mock
    private FinancialInformationDaoImpl financialInformationDaoImpl;
    @Mock
    private FinancialInformationServiceMapper financialInformationServiceMapper;

    @InjectMocks
    private FinancialInformationServiceImpl financialInformationService;

    @Test
    @DisplayName("return Success when get financial information")
    void returnSuccessWhenGetFinancialInformation() {

        when(financialInformationDaoImpl.getCustomerFinancialProducts(anyString(),any()))
                .thenReturn(Mono.just(new CustomerInformationDaoResponse()));
        when(financialInformationServiceMapper.toMap(any()))
                .thenReturn(new CustomerProductsInformationResponse());

        StepVerifier.create(financialInformationService
                        .getCustomerFinancialProducts("12345678",buildHeaders()))
                .expectNext(new CustomerProductsInformationResponse())
                .verifyComplete();
    }

    @Test
    @DisplayName("return empty when get Financial information NotFound")
    void returnEmptyWhenGetFinancialInformationNotFound() {
        when(financialInformationDaoImpl.getCustomerFinancialProducts(anyString(),any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(financialInformationService.getCustomerFinancialProducts(StringUtils
                        .EMPTY, buildHeaders())).verifyComplete();

        verify(financialInformationDaoImpl).getCustomerFinancialProducts(StringUtils.EMPTY,buildHeaders());
        verifyNoInteractions(financialInformationServiceMapper);
    }

    @Test
    @DisplayName("return error when get Financial Information")
    void returnErrorWhenGetFinancialInformation() {
        when(financialInformationDaoImpl.getCustomerFinancialProducts(anyString(),any()))
                .thenReturn(Mono.error(new RuntimeException("error")));
        StepVerifier.create(financialInformationService.getCustomerFinancialProducts(StringUtils.EMPTY,buildHeaders()))
                .expectError(RuntimeException.class)
                .verify();

        verify(financialInformationDaoImpl).getCustomerFinancialProducts(StringUtils.EMPTY,buildHeaders());
        verifyNoInteractions(financialInformationServiceMapper);
    }

}
