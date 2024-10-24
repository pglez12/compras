package com.microservicios.compras.service;

import com.microservicios.compras.model.dto.CompraDTORequest;
import com.microservicios.compras.model.dto.CompraDTOResponse;

/**
 * Interfaz que define los servicios relacionados con las compras.
 * @author grupo1
 */
public interface ComprasService {

	/*
	 * Método en el que llamamos al servicio Eventos y 
	 * calculamos un número random entre precio mínimo y máximo de un evento
	 */
	public Double calcularPrecio(Long id);

	public CompraDTOResponse procesarCompra(CompraDTORequest compraDTORequest);
}
