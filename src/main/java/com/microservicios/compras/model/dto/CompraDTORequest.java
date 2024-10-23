package com.microservicios.compras.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa la solicitud de una compra en el sistema.
 * 
 * Esta clase se utiliza para transferir la información necesaria para
 * realizar una compra, incluyendo detalles del evento, información del
 * comprador y datos de la tarjeta de crédito.
 * @author grupo1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraDTORequest {

    @NotBlank(message = "Debe ingresar un id de evento válido")
    private Long idevento;

    @NotBlank(message = "Debe ingresar un email")
    @Size(min = 2, max = 50, message = "Debe ingresar un email de entre 2 y 50 caracteres")
    private String email;

    @NotBlank(message = "Debe ingresar el nombre del titular de la tarjeta")
    @Size(min = 2, max = 50, message = "Debe ingresar un nombre de entre 2 y 50 caracteres")
    private String nombreTitular;

    @NotBlank(message = "Debe ingresar el número de tarjeta")
    @Size(min = 14, max = 14, message = "Debe ingresar un número de 14 dígitos")
    private String numeroTarjeta;

    @NotBlank(message = "Debe ingresar el mes de caducidad de la tarjeta -entre 01 y 12-")
    @Size(min = 2, max = 2, message = "Debe indicar un mes entre 01 y 12")
    private String mesCaducidad;

    @NotBlank(message = "Debe ingresar el año de caducidad de la tarjeta")
    @Size(min = 2, max = 4, message = "Debe ingresar un año de entre 2 y 4 caracteres numéricos")
    private String yearCaducidad;

    @NotBlank(message = "Debe ingresar el código de seguridad de tres dígitos de la tarjeta")
    @Size(min = 3, max = 3, message = "El código de seguridad debe tener tres caracteres numéricos")
    private String cvv;
}
