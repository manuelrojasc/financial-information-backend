package com.financial.customer.controller;

import com.financial.customer.customer.business.CustomerService;
import com.financial.customer.model.CustomerResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController controller;

    @Test
    @DisplayName("return Success when get customer")
    void returnSuccessWhenGetCustomer(){
        when(customerService.findByUserCode(anyString()))
                .thenReturn(Mono.just(new CustomerResponse()));

        Mono<ResponseEntity<CustomerResponse>> resultMono =
                controller.getCustomerByCode("12345", buildServerWebExchange());

        StepVerifier.create(resultMono)
                .assertNext(responseEntity -> {
                    assertEquals("200 OK", responseEntity.getStatusCode().toString());
                    assertEquals(new CustomerResponse(), responseEntity.getBody());
                }).verifyComplete();

        verify(customerService).findByUserCode("12345");
    }

    private MockServerWebExchange buildServerWebExchange () {
        MockServerHttpRequest serverHttpRequest = MockServerHttpRequest
                .get("/api/v1/")
                .header("requestId","77392336-0e48-4244-9a44-7ed43bcd4647")
                .header("Content-Type","application/json")
                .header("requestDate","2020-11-16T17:15:20.509-0400")
                .build();
        return MockServerWebExchange.builder(serverHttpRequest).build();
    }

 }
