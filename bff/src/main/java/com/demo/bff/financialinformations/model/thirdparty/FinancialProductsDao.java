package com.demo.bff.financialinformations.model.thirdparty;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductsDao {
    private String productType;
    private String nombre;
    private String saldo;

}
