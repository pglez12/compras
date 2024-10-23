package com.microservicios.compras.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservicios.compras.controller.error.EventosResponse;
import com.microservicios.compras.model.dto.EventoDTOResponse;

/**
 * Cliente Feign para interactuar con el servicio de eventos.
 * Este cliente permite realizar llamadas a la API de eventos
 * para obtener información detallada sobre eventos específicos.
 * @author grupo1
 */
@FeignClient(name = "eventos", url= "http://localhost:4444")
public interface ComprasFeignClient {
	
    /**
     * Obtiene los detalles de un evento a través de su ID.
     *
     * @param id del evento que se desea obtener.
     * @return Un objeto EventosResponse que contiene los detalles del evento
     *         encapsulados en un EventoDTOResponse.
     */
	@GetMapping("/eventos/{id}")
	EventosResponse<EventoDTOResponse> detalleEvento(@PathVariable("id") Long id);

}

