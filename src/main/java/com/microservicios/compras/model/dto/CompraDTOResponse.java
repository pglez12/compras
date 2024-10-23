package com.microservicios.compras.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa la respuesta de una compra en el sistema.
 * 
 * @author grupo1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDTOResponse {


    private Long id;

    private Double precio;

    private Date fecha;

    private String email;

    private Long idevento;
}