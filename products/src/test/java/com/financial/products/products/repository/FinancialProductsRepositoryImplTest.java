package com.financial.products.products.repository;


import com.financial.products.products.model.FinancialProductsRepositoryResponse;
import com.financial.products.products.repository.db.ReactiveMongoFinancialProductsRepository;
import com.financial.products.products.repository.impl.FinancialProductsRepositoryImpl;
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
public class FinancialProductsRepositoryImplTest {
    @Mock
    private ReactiveMongoFinancialProductsRepository reactiveMongoFinancialProductsRepository;

    @InjectMocks
    private FinancialProductsRepositoryImpl financialProductsRepository;

    @Test
    @DisplayName("return Success when get products in repository")
    void returnSuccessWhenGetProductsInRepository() {
        FinancialProductsRepositoryResponse mockResponse = new FinancialProductsRepositoryResponse();
        when(reactiveMongoFinancialProductsRepository.findProductsByCic(anyString()))
                .thenReturn(Mono.just(mockResponse));

        StepVerifier.create(financialProductsRepository.findProductsByCic("12345"))
                .expectNext(mockResponse)
                .verifyComplete();

        verify(reactiveMongoFinancialProductsRepository).findProductsByCic("12345");
    }

    @Test
    @DisplayName("return Empty when get products in repository by id not found")
    void returnEmptyWhenGetProductsInRepositoryByIdNotFound() {
        when(reactiveMongoFinancialProductsRepository.findProductsByCic(anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(financialProductsRepository.findProductsByCic(StringUtils.EMPTY))
                .verifyComplete();

        verify(reactiveMongoFinancialProductsRepository).findProductsByCic(StringUtils.EMPTY);
    }

    @Test
    @DisplayName("return error when get products in repository")
    void returnErrorWhenGetProductsInRepository() {
        when(reactiveMongoFinancialProductsRepository.findProductsByCic(anyString()))
                .thenReturn(Mono.error(new RuntimeException("Database error")));

        StepVerifier.create(financialProductsRepository.findProductsByCic(StringUtils.EMPTY))
                .expectError(RuntimeException.class)
                .verify();

        verify(reactiveMongoFinancialProductsRepository).findProductsByCic(StringUtils.EMPTY);
    }
}
