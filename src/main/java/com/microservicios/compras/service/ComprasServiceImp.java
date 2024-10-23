package com.microservicios.compras.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.compras.controller.error.EventosResponse;
import com.microservicios.compras.feignclients.ComprasFeignClient;
import com.microservicios.compras.model.dto.EventoDTOResponse;

/**
 * Implementación del servicio ComprasService.
 * @author grupo1
 */
@Service
public class ComprasServiceImp implements ComprasService{
	
    @Autowired
    private ComprasFeignClient comprasFeignClient;

    public EventoDTOResponse eventoPorId(Long id) {
        EventosResponse<EventoDTOResponse> response = comprasFeignClient.detalleEvento(id);
        return response.getData();
    }
    
    public Double calcularPrecio(Long id) {
    	EventoDTOResponse evento = eventoPorId(id);
    	Double precioMin = evento.getPreciomin();
    	Double precioMax = evento.getPreciomax();
    	Random randomizer = new Random();
    	//value = (rnd.nextDouble() * (max - min)) + min;
    	Double precioEntrada = (randomizer.nextDouble() * (precioMax - precioMin)) + precioMin;
		return precioEntrada;
    	
    }

}
