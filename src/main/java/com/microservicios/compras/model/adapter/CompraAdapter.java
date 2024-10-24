package com.microservicios.compras.model.adapter;

import com.microservicios.compras.model.Compra;
import com.microservicios.compras.model.dto.CompraDTOResponse;

import lombok.NoArgsConstructor;

/**
 * Clase adaptadora para convertir objetos de tipo Compra
 * en DTO
 * 
@author grupo1
 */
@NoArgsConstructor 
public class CompraAdapter {
	
	/**
     * Convierte un objeto Compra en un objeto CompraDTOResponse.
     *
     * @param compra El objeto Compra que se desea adaptar.
     * @return Un objeto CompraDTOResponse que contiene los mismos
     *         datos que el objeto Compra proporcionado.
     */
	public CompraDTOResponse adaptar(Compra compra) {
		return new CompraDTOResponse(compra.getId(), compra.getPrecio(), compra.getFecha(), 
				compra.getEmail(), compra.getEventoid());
	}
	
}