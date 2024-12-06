package com.financial.products.products.repository.db;

import com.financial.products.products.model.FinancialProductsRepositoryResponse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveMongoFinancialProductsRepository extends ReactiveMongoRepository<FinancialProductsRepositoryResponse, String>{
    Mono<FinancialProductsRepositoryResponse> findProductsByCic(String cic);
}
