package com.financial.products.products.business;

import com.financial.product.model.FinancialProductsResponse;
import reactor.core.publisher.Mono;

public interface FinancialProductsService {

    Mono<FinancialProductsResponse> findByUserCode(String cic);
}
