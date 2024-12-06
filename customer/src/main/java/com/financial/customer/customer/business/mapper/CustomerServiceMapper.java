package com.financial.customer.customer.business.mapper;

import com.financial.customer.customer.model.CustomerRepositoryResponse;
import com.financial.customer.model.CustomerResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CustomerServiceMapper {
    CustomerResponse toMap(CustomerRepositoryResponse response);
}
