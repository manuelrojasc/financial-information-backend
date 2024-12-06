package com.financial.customer.customer.repository;

import com.financial.customer.customer.model.CustomerRepositoryResponse;
import com.financial.customer.customer.repository.db.ReactiveMongoCustomerRepository;
import com.financial.customer.customer.repository.impl.CustomerRepositoryImpl;
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
public class CustomerRepositoryImplTest {
    @Mock
    private ReactiveMongoCustomerRepository reactiveMongoCustomerRepository;

    @InjectMocks
    private CustomerRepositoryImpl customerRepository;

    @Test
    @DisplayName("return Success when get customer in repository")
    void returnSuccessWhenGetCustomerInRepository() {
        CustomerRepositoryResponse mockResponse = new CustomerRepositoryResponse();
        when(reactiveMongoCustomerRepository.findByUserCode(anyString()))
                .thenReturn(Mono.just(mockResponse));

        StepVerifier.create(customerRepository.findByUserCode("12345"))
                .expectNext(mockResponse)
                .verifyComplete();

        verify(reactiveMongoCustomerRepository).findByUserCode("12345");
    }

    @Test
    @DisplayName("return Empty when get customer in repository by id not found")
    void returnEmptyWhenGetCustomerInRepositoryByIdNotFound() {
        when(reactiveMongoCustomerRepository.findByUserCode(anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerRepository.findByUserCode(StringUtils.EMPTY))
                .verifyComplete();

        verify(reactiveMongoCustomerRepository).findByUserCode(StringUtils.EMPTY);
    }

    @Test
    @DisplayName("return error when get customer in repository")
    void returnErrorWhenGetCustomerInRepository() {
        when(reactiveMongoCustomerRepository.findByUserCode(anyString()))
                .thenReturn(Mono.error(new RuntimeException("Database error")));

        StepVerifier.create(customerRepository.findByUserCode(StringUtils.EMPTY))
                .expectError(RuntimeException.class)
                .verify();

        verify(reactiveMongoCustomerRepository).findByUserCode(StringUtils.EMPTY);
    }
}
