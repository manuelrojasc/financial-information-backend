package com.financial.products.products.business;

import com.financial.product.model.FinancialProductsResponse;
import com.financial.products.products.business.impl.FinancialProductsServiceImpl;
import com.financial.products.products.business.mapper.FinancialProductsServiceMapper;
import com.financial.products.products.model.FinancialProductsRepositoryResponse;
import com.financial.products.products.repository.FinancialProductsRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinancialProductsServiceImplTest {

    @Mock
    private FinancialProductsRepository financialProductsRepository;

    @Mock
    private FinancialProductsServiceMapper financialProductsServiceMapper;

    @InjectMocks
    private FinancialProductsServiceImpl financialProductsService;

    @Test
    @DisplayName("return Success when get financial products")
    void returnSuccessWhenGetFinancialProducts() {

        when(financialProductsRepository.findProductsByCic(anyString()))
                .thenReturn(Mono.just(new FinancialProductsRepositoryResponse()));
        when(financialProductsServiceMapper.toMap(any()))
                .thenReturn(new FinancialProductsResponse());

        StepVerifier.create(financialProductsService.findByUserCode("12345678"))
                .expectNext(new FinancialProductsResponse())
                .verifyComplete();
    }

    @Test
    @DisplayName("return empty when get financial products NotFound")
    void returnEmptyWhenGetFinancialProductsNotFound() {
        when(financialProductsRepository.findProductsByCic(anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(financialProductsService.findByUserCode(StringUtils.EMPTY))
                .verifyComplete();

        verify(financialProductsRepository).findProductsByCic(StringUtils.EMPTY);
        verifyNoInteractions(financialProductsServiceMapper);
    }

    @Test
    @DisplayName("return error when get financial")
    void returnErrorWhenGetFinancial() {
        when(financialProductsRepository.findProductsByCic(anyString()))
                .thenReturn(Mono.error(new RuntimeException("Database error")));
        StepVerifier.create(financialProductsService.findByUserCode(StringUtils.EMPTY))
                .expectError(RuntimeException.class)
                .verify();

        verify(financialProductsRepository).findProductsByCic(StringUtils.EMPTY);
        verifyNoInteractions(financialProductsServiceMapper);
    }
}