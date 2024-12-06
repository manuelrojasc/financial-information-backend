package com.demo.bff.financialinformations.dao.mapper;

import com.demo.bff.financialinformations.model.thirdparty.CustomerInformationDaoResponse;
import com.demo.bff.financialinformations.model.thirdparty.customer.CustomerProxyResponse;
import com.demo.bff.financialinformations.model.thirdparty.products.FinancialProductsProxyResponse;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FinancialInformationDaoMapper {

    @Mapping(source = "productsProxy.products", target= "products")
    @Mapping(source = "customer.nombre", target= "nombre")
    @Mapping(source = "customer.apellidos", target= "apellidos")
    @Mapping(source = "customer.tipoDocumento", target= "tipoDocumento")
    @Mapping(source = "customer.numeroDocumento", target= "numeroDocumento")
    CustomerInformationDaoResponse toMapDao(CustomerProxyResponse customer, FinancialProductsProxyResponse productsProxy);
}
