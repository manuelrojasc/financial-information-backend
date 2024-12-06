package com.financial.customer.customer.business;

import com.financial.customer.model.CustomerResponse;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerResponse> findByUserCode(String userCode);
}
