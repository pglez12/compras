package com.microservicios.compras.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservicios.compras.controller.error.EventosResponse;
import com.microservicios.compras.model.dto.EventoDTOResponse;

@FeignClient(name = "eventos", url= "http://localhost:4444")
public interface ComprasFeignClient {
	
	@GetMapping("/eventos/{id}")
	EventosResponse<EventoDTOResponse> detalleEvento(@PathVariable("id") Long id);

}

