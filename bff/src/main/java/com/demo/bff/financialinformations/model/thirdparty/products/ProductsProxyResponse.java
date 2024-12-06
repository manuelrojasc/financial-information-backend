package com.demo.bff.financialinformations.model.thirdparty.products;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsProxyResponse {
    private String productType;
    private String nombre;
    private String saldo;
    private String code;
}
