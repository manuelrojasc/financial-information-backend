package com.financial.products.products.repository.impl;

import com.financial.products.products.model.FinancialProductsRepositoryResponse;
import com.financial.products.products.repository.FinancialProductsRepository;
import com.financial.products.products.repository.db.ReactiveMongoFinancialProductsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class FinancialProductsRepositoryImpl implements FinancialProductsRepository {

    @Autowired
    private ReactiveMongoFinancialProductsRepository reactiveMongoFinancialProductsRepository;

    @Override
    public Mono<FinancialProductsRepositoryResponse> findProductsByCic(String cic) {
        return reactiveMongoFinancialProductsRepository.findProductsByCic(cic)
                .doOnSuccess(el -> log.info("Searching products info has success"))
                .doOnError(el -> log.error("an error occurred in db"));
    }
}
