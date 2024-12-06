package com.demo.bff.financialinformations.model.thirdparty;

import com.demo.bff.model.FinancialProducts;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInformationDaoResponse {
    private String nombre;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private List<FinancialProductsDao> products;
}
