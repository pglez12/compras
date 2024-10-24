package com.microservicios.compras.service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservicios.compras.controller.error.EventoNotFoundException;
import com.microservicios.compras.controller.error.EventosResponse;
import com.microservicios.compras.feignclients.BancoFeignClient;
import com.microservicios.compras.feignclients.ComprasFeignClient;
import com.microservicios.compras.model.dto.BancoDTORequest;
import com.microservicios.compras.model.dto.CompraDTORequest;
import com.microservicios.compras.model.dto.CompraDTOResponse;
import com.microservicios.compras.model.dto.EventoDTOResponse;

/**
 * Implementación del servicio ComprasService.
 * @author grupo1
 */
@Service
public class ComprasServiceImp implements ComprasService{
	
    @Autowired
    private ComprasFeignClient comprasFeignClient;
    
    @Autowired
    private BancoFeignClient bancoFeignClient;
    
  //------------------------------------------Paula-----------------------------------------------------//
	
	/*public Compra crearCompra(CompraDTOResponse compraDTOResponse) {
		
		EventoDTOResponse eventoDTOResponse = comprasFeignClient.getEventoById(compraDTOResponse.getIdEvento());
		
		if(eventoDTOResponse.getDisponible()) {
			
			Compra compra = new Compra();
			
			compra.setIdevento(compraDTOResponse.getIdevento());
			compra.setEmail(compraDTOResponse.getEmail());
			compra.setFecha(compraDTOResponse.getFecha());
			compra.setPrecio(compraDTOResponse.getPrecio());
			
			return compraRepository.save(compra);
			
		}else {
			throw new EventoNotFoundException(eventoDTOResponse.getMensaje());
			
		}
		
	}*/
    
 //------------------------------------------Paloma-----------------------------------------------------//
    
    /**
     * Orquesta el proceso de compra.
     *
     * @param compraDTORequest
     * @return CompraDTOResponse la respuesta de la compra.
     */
    public CompraDTOResponse procesarCompra(CompraDTORequest compraDTORequest) {
        Double precio = calcularPrecio(compraDTORequest.getIdevento());
        BancoDTORequest bancoRequest = crearBancoDTORequest(compraDTORequest, precio);
        ResponseEntity<?> response = realizarTransaccion(bancoRequest);

        return manejarRespuesta(response, precio, compraDTORequest);
    }
    

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
    
    /**
     * Construye un objeto BancoDTORequest que se utilizará para realizar la transacción 
     * bancaria.
     *
     * @param compraDTORequest contiene la información de la compra.
     * @param precio calculado de la entrada.
     * @return BancoDTORequest para ser enviado al servicio bancario.
     */
    private BancoDTORequest crearBancoDTORequest(CompraDTORequest compraDTORequest, Double precio) {
        return BancoDTORequest.builder()
            .nombreTitular(compraDTORequest.getNombreTitular())
            .numeroTarjeta(compraDTORequest.getNumeroTarjeta())
            .mesCaducidad(compraDTORequest.getMesCaducidad())
            .yearCaducidad(compraDTORequest.getYearCaducidad())
            .cvv(compraDTORequest.getCvv())
            .emisor("CapTickets")
            .concepto("Compra de entrada para evento ID: " + compraDTORequest.getIdevento())
            .cantidad(precio)
            .build();
    }
    
    /**
     * Realiza la transacción bancaria llamando al cliente Feign bancoFeignClient
     *
     * @param bancoRequestcontiene la información de la transacción bancaria.
     * @return ResponseEntity con la respuesta del servicio bancario.
     */
    private ResponseEntity<?> realizarTransaccion(BancoDTORequest bancoRequest) {
        return bancoFeignClient.realizarCompra(bancoRequest);
    }

    /**
     * Evalúa la respuesta de la transacción.
     * Si es exitosa, crea y devuelve un objeto CompraDTOResponse
     * Si hay un error, lanza una excepción.
     *
     * @param response del servicio bancario.
     * @param precio de la entrada.
     * @param compraDTORequest que contiene la información de la compra.
     * @return CompraDTOResponse si la transacción es exitosa.
     * @throws RuntimeException Si la transacción falla.
     */
    //AÑADIR GESTION DE EXCEPCIONES
    private CompraDTOResponse manejarRespuesta(ResponseEntity<?> response, Double precio, CompraDTORequest compraDTORequest) {
        if (response.getStatusCode().is2xxSuccessful()) {
        	//Meter compra en tabla
            return crearCompraDTOResponse(precio, compraDTORequest);
        } else {
            throw new RuntimeException("Error en la compra: " + response.getBody());
        }
    }
    
    /**
     * Construye un objeto CompraDTOResponse}
     *
     * @param precio de la entrada.
     * @param compraDTORequest información de la compra.
     * @return CompraDTOResponse representa la respuesta de la compra.
     */
    private CompraDTOResponse crearCompraDTOResponse(Double precio, CompraDTORequest compraDTORequest) {
        return CompraDTOResponse.builder()
            //.id() aun no lo tengo porque se genera cuando creamosla instancia en la tablas
            .precio(precio)
            .fecha(new Date()) // tiene que se  timestamp no?
            .email(compraDTORequest.getEmail())
            .idevento(compraDTORequest.getIdevento())
            .build();
    }

    /*public String procesarCompra(CompraDTORequest compraDTORequest) {

    Double precio = calcularPrecio(compraDTORequest.getIdevento());

    BancoDTORequest bancoRequest = BancoDTORequest.builder()
        .nombreTitular(compraDTORequest.getNombreTitular())
        .numeroTarjeta(compraDTORequest.getNumeroTarjeta())
        .mesCaducidad(compraDTORequest.getMesCaducidad())
        .yearCaducidad(compraDTORequest.getYearCaducidad())
        .cvv(compraDTORequest.getCvv())
        .emisor("CapTickets")
        .concepto("Compra de entrada para evento ID: " + compraDTORequest.getIdevento())
        .cantidad(precio)
        .build();

    ResponseEntity<?> response = bancoFeignClient.realizarCompra(bancoRequest);

    if (response.getStatusCode().is2xxSuccessful()) {
        return "Compra exitosa";
    } else {
        return "Error en la compra: " + response.getBody();
    }
}*/
}
