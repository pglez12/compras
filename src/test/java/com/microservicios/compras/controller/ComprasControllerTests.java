package com.microservicios.compras.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.microservicios.compras.controller.error.EventosResponse;
import com.microservicios.compras.model.dto.CompraDTORequest;
import com.microservicios.compras.model.dto.CompraDTOResponse;
import com.microservicios.compras.service.ComprasService;

public class ComprasControllerTests {

	    @InjectMocks
	    private CompraController compraController;

	    @Mock
	    private ComprasService comprasService;

	    private CompraDTORequest compraDTORequest;
	    private CompraDTOResponse compraDTOResponse;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);

	        // Inicializar los objetos de prueba
	        compraDTORequest = new CompraDTORequest();
	        // Asigna propiedades necesarias para la prueba
	        compraDTORequest.setIdevento(1L);
	        compraDTORequest.setEmail("test@example.com");

	        compraDTOResponse = new CompraDTOResponse();
	        // Asigna propiedades necesarias para la respuesta
	        compraDTOResponse.setPrecio(15.0);
	        compraDTOResponse.setEmail(compraDTORequest.getEmail());
	    }

	    /**
	     * Prueba el método realizarCompra de CompraController.
	     * Verifica que se procese correctamente una compra y se devuelva una respuesta exitosa.
	     */
	    @Test
	    public void testRealizarCompraExito() {
	        when(comprasService.procesarCompra(compraDTORequest)).thenReturn(compraDTOResponse);

	        ResponseEntity<EventosResponse<CompraDTOResponse>> response = compraController.realizarCompra(compraDTORequest);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("200", response.getBody().getCode());
	        assertEquals("Compra realizada con éxito", response.getBody().getMessage());
	        assertEquals(compraDTOResponse, response.getBody().getData());
	    }

	    /**
	     * Prueba el método realizarCompra de CompraController.
	     * Verifica que se maneje correctamente un error en la compra lanzando la excepción esperada.
	     */
	    @Test
	    public void testRealizarCompraError() {
	        when(comprasService.procesarCompra(compraDTORequest)).thenThrow(new RuntimeException("Error en la compra"));

	        ResponseEntity<EventosResponse<CompraDTOResponse>> response = compraController.realizarCompra(compraDTORequest);

	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	        assertEquals("500", response.getBody().getCode());
	        assertEquals("Error al procesar la compra", response.getBody().getMessage());
	    }

	    /**
	     * Prueba el método fallBackRealizarCompra de CompraController.
	     * Verifica que se devuelva una respuesta de fallback cuando ocurre un error en el servicio.
	     */
	    /*@Test
	    public void testFallBackRealizarCompra() {
	        ResponseEntity<EventosResponse<CompraDTOResponse>> response = compraController.fallBackRealizarCompra(compraDTORequest, new Throwable("Servicio no disponible"));

	        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	        assertEquals("503", response.getBody().getCode());
	        assertEquals("Servicio de compras no disponible. Inténtelo más tarde.", response.getBody().getMessage());
	    }*/
}
