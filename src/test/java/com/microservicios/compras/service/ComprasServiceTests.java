package com.microservicios.compras.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;


import com.microservicios.compras.controller.error.EventosResponse;
import com.microservicios.compras.feignclients.BancoFeignClient;
import com.microservicios.compras.feignclients.ComprasFeignClient;
import com.microservicios.compras.model.dto.BancoDTORequest;
import com.microservicios.compras.model.dto.CompraDTORequest;
import com.microservicios.compras.model.dto.CompraDTOResponse;
import com.microservicios.compras.model.dto.EventoDTOResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase de prueba para el servicio ComprasServiceImp.
 * @author grupo1
 */
@Slf4j
public class ComprasServiceTests {

    @InjectMocks
    private ComprasServiceImp comprasService;

    @Mock
    private ComprasFeignClient comprasFeignClient;

    @Mock
    private BancoFeignClient bancoFeignClient;
    private EventoDTOResponse eventoDTOResponse;
    private EventoDTOResponse eventoDTOResponse2;
    private CompraDTORequest compraDTORequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        eventoDTOResponse = new EventoDTOResponse();
        eventoDTOResponse.setNombre("The Eras Tour");
        eventoDTOResponse.setId(1L);
        eventoDTOResponse.setPreciomin(10.0);
        eventoDTOResponse.setPreciomax(20.0);
        eventoDTOResponse.setActivo(true);
        eventoDTOResponse2 = new EventoDTOResponse();
        eventoDTOResponse2.setNombre("Loverfest");
        eventoDTOResponse2.setId(2L);
        eventoDTOResponse2.setPreciomin(30.0);
        eventoDTOResponse2.setPreciomax(40.0);
        eventoDTOResponse2.setActivo(true);
        
        compraDTORequest = new CompraDTORequest();
        compraDTORequest.setIdevento(1L);
        compraDTORequest.setEmail("test@example.com");
        compraDTORequest.setNombreTitular("John Doe");
        compraDTORequest.setNumeroTarjeta("12345678901234");
        compraDTORequest.setMesCaducidad("12");
        compraDTORequest.setYearCaducidad("25");
        compraDTORequest.setCvv("123");
    }

    /**
     * Prueba el método eventoPorId de ComprasServiceImp.
     * Verifica que el método devuelva el evento correcto al
     * consultar por su ID, utilizando un cliente Feign simulado.
     */
    @Test
    public void testEventoPorId() {
    	EventoDTOResponse eventoTest = eventoDTOResponse2;
    	Long idTest = 2L;

        EventosResponse<EventoDTOResponse> eventosResponse = new EventosResponse<>();
        eventosResponse.setData(eventoTest);
        log.info("-------Service Test: eventoPorId()-------");
        
        when(comprasFeignClient.detalleEvento(anyLong())).thenReturn(eventosResponse);
        
        EventoDTOResponse result = comprasService.eventoPorId(idTest);
        Long idResponse = result.getId();
        String nombreResponse = result.getNombre();
        log.info("Evento recogido: " + nombreResponse + " con id: " + idResponse);
        assertEquals(eventoTest, result);
        log.info("~~Comprobado que el resultado devuelto es igual a elemento introducido~~");
    }

    /**
     * Prueba el método calcularPrecio de ComprasServiceImp.
     * Verifica que el precio calculado esté dentro del rango
     * permitido según los precios mínimo y máximo del evento.
     */
    @Test
    public void testCalcularPrecio() {
        EventosResponse<EventoDTOResponse> eventosResponse = new EventosResponse<>();
        eventosResponse.setData(eventoDTOResponse);
        log.info("-------Service Test: calcularPrecio()-------");
        when(comprasFeignClient.detalleEvento(anyLong())).thenReturn(eventosResponse);
        
        Double precio = comprasService.calcularPrecio(1L);
        log.info("compraService ha calcularo este precio:" + precio);
        assertEquals(true, precio >= 10.0 && precio <= 20.0);
        log.info("~~Comprobado que el resultado esta entre precio minimo y máximo~~");
    }
//--------------------------------nueva parte-----------------------------------------//
    /**
     * Prueba el método procesarCompra de ComprasServiceImp.
     * Verifica que el método procese correctamente una compra
     * y devuelva un CompraDTOResponse cuando la transacción es exitosa.
     */
    @Test
    public void testProcesarCompraExito() {
        log.info("-------Service Test: procesarCompraExito()-------");

        when(comprasFeignClient.detalleEvento(anyLong())).thenReturn(new EventosResponse<>() {{
            setData(eventoDTOResponse);
        }});
        
        when(bancoFeignClient.realizarCompra(any(BancoDTORequest.class)))
            .thenReturn(ResponseEntity.ok().build());

        CompraDTOResponse resultado = comprasService.procesarCompra(compraDTORequest);

        assertEquals(compraDTORequest.getEmail(), resultado.getEmail());
        log.info("~~Compra procesada con éxito~~");
    }

    
}
