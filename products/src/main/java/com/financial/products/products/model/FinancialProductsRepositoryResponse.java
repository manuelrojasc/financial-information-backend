package com.financial.products.products.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class FinancialProductsRepositoryResponse {
    @Id
    private String id;
    private String cic;
    private List<ProductRepository> products;
}
