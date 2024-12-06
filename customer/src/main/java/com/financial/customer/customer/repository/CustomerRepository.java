package com.financial.customer.customer.repository;

import com.financial.customer.customer.model.CustomerRepositoryResponse;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
    Mono<CustomerRepositoryResponse> findByUserCode(String userCode);
}
