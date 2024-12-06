package com.demo.encrypt.encrypt.business.impl;


import com.demo.encrypt.config.EncryptionProperties;
import com.demo.encrypt.encrypt.business.AesEncryptionService;
import com.demo.encrypt.model.EncriptResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class AesEncryptionServiceImpl implements AesEncryptionService {

    @Autowired
    private EncryptionProperties encryptionProperties;

    @Override
    public Mono<EncriptResponse> encrypt(String plainText) {
        try {
            return Mono.just(new EncriptResponse().encryptedString(encryptImpl(plainText)));
        } catch (Exception e) {
            log.error("Encryption error", e);
            return Mono.error(new RuntimeException("Encryption failed", e));
        }
    }


    private String encryptImpl(String plainText) {
        try {

            SecretKeySpec secretKey = new SecretKeySpec(
                    encryptionProperties.getSecretKey().getBytes(StandardCharsets.UTF_8),
                    encryptionProperties.getBaseAlgorithm()
            );

            Cipher cipher = Cipher.getInstance(encryptionProperties.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("Encryption error", e);
            throw new RuntimeException("Encryption failed", e);
        }
    }

}
