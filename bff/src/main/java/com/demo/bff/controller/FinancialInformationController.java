package com.demo.bff.controller;

import com.demo.bff.config.EncryptionProperties;
import com.demo.bff.financialinformations.business.FinancialInformationService;
import com.demo.bff.model.CustomerProductsInformationResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Slf4j
@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class FinancialInformationController implements CustomerInformationApi {

    @Autowired
    private  EncryptionProperties encryptionProperties;
    @Autowired
    private  FinancialInformationService financialInformationService;

    @Override
    public Mono<ResponseEntity<CustomerProductsInformationResponse>> getCustomerFinancialProducts(String codigoUnico, ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        log.info("requestId: {}",headers.get("requestId"));
        return financialInformationService.getCustomerFinancialProducts(decrypt(codigoUnico),headers)
                .doOnSubscribe(el -> log.info("Endpoint GET has stated"))
                .map(ResponseEntity::ok)
                .doOnSuccess(el -> log.info("Endpoint GET has completed"));
    }



    private String decrypt(String encryptedText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(
                    encryptionProperties.getSecretKey().getBytes(StandardCharsets.UTF_8),
                    encryptionProperties.getBaseAlgorithm());

            Cipher cipher = Cipher.getInstance(encryptionProperties.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Decryption error", e);
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
