package com.financial.products.products.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRepository {
    private String productType;
    private String nombre;
    private String saldo;
    private String code;
}
