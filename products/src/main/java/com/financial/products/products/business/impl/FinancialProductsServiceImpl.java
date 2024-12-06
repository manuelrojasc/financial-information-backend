package com.financial.products.products.business.impl;

import com.financial.product.model.FinancialProductsResponse;
import com.financial.products.products.business.FinancialProductsService;
import com.financial.products.products.business.mapper.FinancialProductsServiceMapper;
import com.financial.products.products.repository.FinancialProductsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class FinancialProductsServiceImpl implements FinancialProductsService {
    @Autowired
    private FinancialProductsRepository financialProductsRepository;
    @Autowired
    private FinancialProductsServiceMapper financialProductsServiceMapper;

    @Override
    public Mono<FinancialProductsResponse> findByUserCode(String cic) {
        return financialProductsRepository.findProductsByCic(cic)
                .map(financialProductsServiceMapper::toMap);
    }
}
