package com.microservicios.compras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.compras.controller.error.EventosResponse;
import com.microservicios.compras.model.dto.CompraDTORequest;
import com.microservicios.compras.model.dto.CompraDTOResponse;
import com.microservicios.compras.service.ComprasService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private ComprasService comprasService;
    
    @CircuitBreaker(name = "compracb", fallbackMethod = "fallBackRealizarCompra")
    @PostMapping
    public ResponseEntity<EventosResponse<CompraDTOResponse>> realizarCompra(@Valid @RequestBody CompraDTORequest compraDTORequest) {
        try {
            CompraDTOResponse compraResponse = comprasService.procesarCompra(compraDTORequest);
            return ResponseEntity.ok(new EventosResponse<>("200", "OK", "Compra realizada con éxito", compraResponse, null));
        } catch (RuntimeException e) {
            log.error("Error al realizar la compra: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EventosResponse<>("500", "Internal Server Error", "Error al procesar la compra", null, null));
        }
    }

    private ResponseEntity<EventosResponse<CompraDTOResponse>> fallBackRealizarCompra(@RequestBody CompraDTORequest compraDTORequest, Throwable e) {
        log.warn("Fallback para realizar compra: {}", e.getMessage());
        return new ResponseEntity<>(new EventosResponse<>("503", "Service Unavailable", "Servicio de compras no disponible. Inténtelo más tarde.", null, null), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
