package com.demo.encrypt.controller;

import com.demo.encrypt.encrypt.business.AesEncryptionService;
import com.demo.encrypt.model.EncriptResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class EncryptController implements EncryptApi{
    @Autowired
    private AesEncryptionService aesEncryptionService;

    @Override
    public Mono<ResponseEntity<EncriptResponse>> encryptString(String data, ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        log.info("requestId: {}",headers.get("requestId"));
        return aesEncryptionService.encrypt(data)
                .doOnSubscribe(el -> log.info("Endpoint POST has stated"))
                .map(ResponseEntity::ok)
                .doOnSuccess(el -> log.info("Endpoint POST has completed"));
    }
}
