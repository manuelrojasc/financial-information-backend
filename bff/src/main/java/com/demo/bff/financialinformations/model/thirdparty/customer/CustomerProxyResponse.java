package com.demo.bff.financialinformations.model.thirdparty.customer;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProxyResponse {
    private String nombre;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
}
