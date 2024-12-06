package com.financial.customer.customer.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer")
public class CustomerRepositoryResponse {
    @Id
    private String id;
    private String userCode;
    private String nombre;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
}
