package com.financial.products.controller;

import com.financial.product.model.FinancialProductsResponse;
import com.financial.products.products.business.FinancialProductsService;
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
public class FinancialProductsControllerTest {

    @Mock
    private FinancialProductsService financialProductService;

    @InjectMocks
    private FinancialProductsController controller;

    @Test
    @DisplayName("return Success when get financial products")
    void returnSuccessWhenGetFinancialProducts(){
        when(financialProductService.findByUserCode(anyString()))
                .thenReturn(Mono.just(new FinancialProductsResponse()));

        Mono<ResponseEntity<FinancialProductsResponse>> resultMono =
                controller.getFinancialProducts("12345", buildServerWebExchange());

        StepVerifier.create(resultMono)
                .assertNext(responseEntity -> {
                    assertEquals("200 OK", responseEntity.getStatusCode().toString());
                    assertEquals(new FinancialProductsResponse(), responseEntity.getBody());
                }).verifyComplete();

        verify(financialProductService).findByUserCode("12345");
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
