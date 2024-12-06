package com.demo.encrypt.encrypt.business;

import com.demo.encrypt.config.EncryptionProperties;
import com.demo.encrypt.encrypt.business.impl.AesEncryptionServiceImpl;
import com.demo.encrypt.model.EncriptResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AesEncryptionServiceImplTest {
    @Mock
    EncryptionProperties encryptionProperties;
    @InjectMocks
    private AesEncryptionServiceImpl aesEncryptionService;

    @Test
    @DisplayName("return Success when encrypt data")
    void returnSuccessWhenEncryptData() {

        when(encryptionProperties.getAlgorithm()).thenReturn("AES/ECB/PKCS5Padding");
        when(encryptionProperties.getSecretKey()).thenReturn("TXLrNhQOgmrkAs8m");
        when(encryptionProperties.getBaseAlgorithm()).thenReturn("AES");

        StepVerifier.create(aesEncryptionService.encrypt("12345678"))
                .expectNext(new EncriptResponse().encryptedString("sUp82IejejOKknkm8eF8Bw=="))
                .verifyComplete();
    }

    @Test
    @DisplayName("return Error when encrypt data")
    void returnErrorWhenEncryptData() {
        StepVerifier.create(aesEncryptionService.encrypt(StringUtils.EMPTY))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                "Encryption failed".equals(throwable.getMessage()))
                .verify();
    }

}
