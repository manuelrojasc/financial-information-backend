package com.demo.bff.financialinformations.model.thirdparty.products;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductsProxyResponse {
    private String cic;
    private List<ProductsProxyResponse> products;
}
