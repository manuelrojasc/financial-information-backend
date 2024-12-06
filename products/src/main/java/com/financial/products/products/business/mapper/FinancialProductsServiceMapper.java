package com.financial.products.products.business.mapper;

import com.financial.product.model.FinancialProductsResponse;
import com.financial.products.products.model.FinancialProductsRepositoryResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FinancialProductsServiceMapper {
    FinancialProductsResponse toMap(FinancialProductsRepositoryResponse response);
}
