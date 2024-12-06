package com.financial.customer.customer.business.impl;

import com.financial.customer.customer.business.CustomerService;
import com.financial.customer.customer.business.mapper.CustomerServiceMapper;
import com.financial.customer.customer.repository.CustomerRepository;
import com.financial.customer.model.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerServiceMapper customerServiceMapper;

    @Override
    public Mono<CustomerResponse> findByUserCode(String userCode) {
        return customerRepository
                .findByUserCode(userCode)
                .map(customerServiceMapper::toMap);
    }
}
