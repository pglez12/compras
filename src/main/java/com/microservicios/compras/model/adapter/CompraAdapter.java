package com.microservicios.compras.model.adapter;

import com.microservicios.compras.model.Compra;
import com.microservicios.compras.model.dto.CompraDTOResponse;

import lombok.NoArgsConstructor;

@NoArgsConstructor 
public class CompraAdapter {
	
	public CompraDTOResponse adaptar(Compra compra) {
		return new CompraDTOResponse(compra.getId(), compra.getPrecio(), compra.getFecha(), 
				compra.getEmail(), compra.getIdevento());
	}
	
}