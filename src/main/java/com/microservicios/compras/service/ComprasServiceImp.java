package com.microservicios.compras.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.compras.controller.error.EventoNotFoundException;
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

    /**
     * Obtiene un evento por su ID.
     *
     * @param id del evento que se desea obtener.
     * @return objeto EventoDTOResponse correspondiente al evento.
     * @throws EventoNotFoundException si no se encuentra el evento con el ID proporcionado.
     */
    public EventoDTOResponse eventoPorId(Long id) {
        EventosResponse<EventoDTOResponse> response = comprasFeignClient.detalleEvento(id);
        
        return Optional.ofNullable(response)
                .map(EventosResponse::getData)
                .orElseThrow(() -> new EventoNotFoundException(id));
    }

    /**
     * Calcula un precio aleatorio para la entrada de un evento dado su ID.
     *
     * @param id del evento para el cual se desea calcular el precio.
     * @return El precio aleatorio calculado dentro del rango
     *         definido por los precios mínimo y máximo del evento.
     * @throws EventoNotFoundException si no se encuentra el evento con el ID proporcionado.
     */
    public Double calcularPrecio(Long id) {
        EventoDTOResponse evento = eventoPorId(id);
        
        // Obtiene los precios mínimo y máximo del evento
        Double precioMin = evento.getPreciomin();
        Double precioMax = evento.getPreciomax();
        
        //value = (rnd.nextDouble() * (max - min)) + min;
        Random randomizer = new Random();
        Double precioEntrada = (randomizer.nextDouble() * (precioMax - precioMin)) + precioMin;
        
        return precioEntrada;
    }

}
