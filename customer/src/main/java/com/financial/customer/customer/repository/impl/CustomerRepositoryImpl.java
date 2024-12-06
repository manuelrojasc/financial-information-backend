package com.financial.customer.customer.repository.impl;

import com.financial.customer.customer.model.CustomerRepositoryResponse;
import com.financial.customer.customer.repository.CustomerRepository;
import com.financial.customer.customer.repository.db.ReactiveMongoCustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private ReactiveMongoCustomerRepository reactiveMongoCustomerRepository;

    @Override
    public Mono<CustomerRepositoryResponse> findByUserCode(String userCode) {
        return reactiveMongoCustomerRepository.findByUserCode(userCode)
                .doOnSuccess(el -> log.info("Searching info has success"))
                .doOnError(el -> log.error("an error occurred in db"));
    }
}
