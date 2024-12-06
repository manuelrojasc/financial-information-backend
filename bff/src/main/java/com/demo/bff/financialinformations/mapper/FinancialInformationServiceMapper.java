package com.demo.bff.financialinformations.mapper;

import com.demo.bff.financialinformations.model.thirdparty.CustomerInformationDaoResponse;
import com.demo.bff.model.CustomerProductsInformationResponse;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FinancialInformationServiceMapper {
    CustomerProductsInformationResponse toMap(CustomerInformationDaoResponse response);
}
