package com.microservicios.compras.service;

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
}
