package com.demo.bff.controller;

import com.demo.bff.config.EncryptionProperties;
import com.demo.bff.financialinformations.business.FinancialInformationService;
import com.demo.bff.model.CustomerProductsInformationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.demo.bff.TestUtil.buildServerWebExchange;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinancialInformationControllerTest {
    @Mock
    private FinancialInformationService financialInformationService;

    @Mock
    EncryptionProperties encryptionProperties;

    @InjectMocks
    private FinancialInformationController controller;

    @Test
    @DisplayName("return Success when get financial information")
    void returnSuccessWhenGetFinancialInformation(){
        when(financialInformationService.getCustomerFinancialProducts(anyString(),any()))
                .thenReturn(Mono.just(new CustomerProductsInformationResponse()));
        when(encryptionProperties.getAlgorithm()).thenReturn("AES/ECB/PKCS5Padding");
        when(encryptionProperties.getSecretKey()).thenReturn("TXLrNhQOgmrkAs8m");
        when(encryptionProperties.getBaseAlgorithm()).thenReturn("AES");

        Mono<ResponseEntity<CustomerProductsInformationResponse>> resultMono =
                controller.getCustomerFinancialProducts("Q+RT028olh8fnRLUCaOMIg==", buildServerWebExchange());

        StepVerifier.create(resultMono)
                .assertNext(responseEntity -> {
                    assertEquals("200 OK", responseEntity.getStatusCode().toString());
                    assertEquals(new CustomerProductsInformationResponse(), responseEntity.getBody());
                }).verifyComplete();

    }

}
