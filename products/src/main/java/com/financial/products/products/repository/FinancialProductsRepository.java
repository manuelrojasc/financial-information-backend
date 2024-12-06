package com.financial.products.products.repository;

import com.financial.products.products.model.FinancialProductsRepositoryResponse;
import reactor.core.publisher.Mono;

public interface FinancialProductsRepository {
    Mono<FinancialProductsRepositoryResponse> findProductsByCic(String cic);
}
