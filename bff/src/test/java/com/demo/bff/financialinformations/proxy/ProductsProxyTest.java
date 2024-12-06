package com.demo.bff.financialinformations.proxy;

import com.demo.bff.financialinformations.model.thirdparty.customer.CustomerProxyResponse;
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
public class ProductsProxyTest {
    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CustomerProxy customerProxy;

    @BeforeEach
    void setup() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.headers(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        ReflectionTestUtils.setField(customerProxy, "baseUrl", "http://test-url/");
    }


    @Test
    @DisplayName("return Success when get customer proxy")
    void returnSuccessWhenGetProductProxy() {
        CustomerProxyResponse mockResponse = new CustomerProxyResponse();

        when(responseSpec.bodyToMono(CustomerProxyResponse.class))
                .thenReturn(Mono.just(mockResponse));

        StepVerifier.create(customerProxy.getCustomerByCode("12345", buildHeaders()))
                .expectNext(mockResponse).verifyComplete();
    }

    @Test
    @DisplayName("return error when web client error")
    void returnErrorWhenWebClientError() {

        when(responseSpec.bodyToMono(CustomerProxyResponse.class))
                .thenReturn(Mono.error(WebClientResponseException
                        .create(500, "Internal Server Error", null, null, null)));

        StepVerifier.create(customerProxy.getCustomerByCode(StringUtils.EMPTY, buildHeaders()))
                .expectError(WebClientResponseException.class)
                .verify();
    }

    @Test
    @DisplayName("return Error when unexpected error")
    void returnErrorWhenUnexpectedError() {

        when(responseSpec.bodyToMono(CustomerProxyResponse.class))
                .thenReturn(Mono.error(new RuntimeException("Unexpected error")));

        StepVerifier.create(customerProxy.getCustomerByCode(StringUtils
                        .EMPTY, buildHeaders()))
                .expectError(RuntimeException.class).verify();
    }
}
