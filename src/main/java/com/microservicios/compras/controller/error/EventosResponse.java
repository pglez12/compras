package com.microservicios.compras.controller.error;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa la estructura de respuesta para eventos en el sistema de compras.
 * Esta clase se utiliza para encapsular la información de respuesta, incluyendo códigos de estado,
 * mensajes y datos, así como posibles errores que puedan surgir durante el procesamiento.
 *
 * @param <T> El tipo de datos que se incluirán en la respuesta.
 * @author grupo1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventosResponse<T> {

	 private String timestamp;
	    private String code;
	    private String status;
	    private String message;
	    private T data;
	    private List<String> errors;

	    public EventosResponse(String code, String status, String message, T data, List<String> errors) {
	        this.timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	        this.code = code;
	        this.status = status;
	        this.message = message;
	        this.data = data;
	        this.errors = errors;
	    }
}
