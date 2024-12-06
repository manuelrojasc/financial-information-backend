package com.financial.customer.customer.business;

import com.financial.customer.customer.business.impl.CustomerServiceImpl;
import com.financial.customer.customer.business.mapper.CustomerServiceMapper;
import com.financial.customer.customer.model.CustomerRepositoryResponse;
import com.financial.customer.customer.repository.CustomerRepository;
import com.financial.customer.model.CustomerResponse;
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
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerServiceMapper customerServiceMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("return Success when get customer")
    void returnSuccessWhenGetCustomer() {

        when(customerRepository.findByUserCode(anyString()))
                .thenReturn(Mono.just(new CustomerRepositoryResponse()));
        when(customerServiceMapper.toMap(any()))
                .thenReturn(new CustomerResponse());

        StepVerifier.create(customerService.findByUserCode("12345678"))
                .expectNext(new CustomerResponse())
                .verifyComplete();
    }

    @Test
    @DisplayName("return empty when get customer NotFound")
    void returnEmptyWhenGetCustomerNotFound() {
        when(customerRepository.findByUserCode(anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerService.findByUserCode(StringUtils.EMPTY))
                .verifyComplete();

        verify(customerRepository).findByUserCode(StringUtils.EMPTY);
        verifyNoInteractions(customerServiceMapper);
    }

    @Test
    @DisplayName("return error when get customer")
    void returnErrorWhenGetCustomer() {
        when(customerRepository.findByUserCode(anyString()))
                .thenReturn(Mono.error(new RuntimeException("Database error")));
        StepVerifier.create(customerService.findByUserCode(StringUtils.EMPTY))
                .expectError(RuntimeException.class)
                .verify();

        verify(customerRepository).findByUserCode(StringUtils.EMPTY);
        verifyNoInteractions(customerServiceMapper);
    }
}