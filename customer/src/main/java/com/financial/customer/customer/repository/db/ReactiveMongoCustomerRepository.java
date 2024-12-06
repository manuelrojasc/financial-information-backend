package com.financial.customer.customer.repository.db;

import com.financial.customer.customer.model.CustomerRepositoryResponse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveMongoCustomerRepository extends ReactiveMongoRepository<CustomerRepositoryResponse, String> {
    Mono<CustomerRepositoryResponse> findByUserCode(String dni);
}
