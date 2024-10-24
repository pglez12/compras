package com.microservicios.compras.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BancoDTORequest {

    @NotBlank(message = "El nombre del titular es obligatorio")
    private String nombreTitular;

    @NotBlank(message = "El número de tarjeta es obligatorio")
    private String numeroTarjeta;

    @NotBlank(message = "El mes de caducidad es obligatorio")
    private String mesCaducidad;

    @NotBlank(message = "El año de caducidad es obligatorio")
    private String yearCaducidad;

    @NotBlank(message = "El código CVV es obligatorio")
    private String cvv;

    private String emisor;
    private String concepto; // nombre del evento
    private Double cantidad; // precio de la compra
}
