package com.demo.bff.financialinformations.proxy;

import com.demo.bff.financialinformations.model.thirdparty.products.FinancialProductsProxyResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.demo.bff.TestUtil.buildHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerProxyTest {
    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ProductsProxy productsProxy;

    @BeforeEach
    void setup() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.headers(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        ReflectionTestUtils.setField(productsProxy, "baseUrl", "http://test-url/");
    }


    @Test
    @DisplayName("return Success when get products proxy")
    void returnSuccessWhenGetProductProxy() {
        FinancialProductsProxyResponse mockResponse = new FinancialProductsProxyResponse();

        when(responseSpec.bodyToMono(FinancialProductsProxyResponse.class))
                .thenReturn(Mono.just(mockResponse));

        StepVerifier.create(productsProxy.getFinancialProducts("12345", buildHeaders()))
                .expectNext(mockResponse).verifyComplete();
    }

    @Test
    @DisplayName("return error when web client error")
    void returnErrorWhenWebClientError() {

        when(responseSpec.bodyToMono(FinancialProductsProxyResponse.class))
                .thenReturn(Mono.error(WebClientResponseException
                        .create(500, "Internal Server Error", null, null, null)));

        StepVerifier.create(productsProxy.getFinancialProducts(StringUtils.EMPTY, buildHeaders()))
                .expectError(WebClientResponseException.class)
                .verify();
    }

    @Test
    @DisplayName("return Error when unexpected error")
    void returnErrorWhenUnexpectedError() {

        when(responseSpec.bodyToMono(FinancialProductsProxyResponse.class))
                .thenReturn(Mono.error(new RuntimeException("Unexpected error")));

        StepVerifier.create(productsProxy.getFinancialProducts(StringUtils
                        .EMPTY, buildHeaders()))
                .expectError(RuntimeException.class).verify();
    }
}
