package com.demo.encrypt.encrypt.business;

import com.demo.encrypt.model.EncriptResponse;
import reactor.core.publisher.Mono;

public interface AesEncryptionService {
    Mono<EncriptResponse> encrypt(String plainText);
}
