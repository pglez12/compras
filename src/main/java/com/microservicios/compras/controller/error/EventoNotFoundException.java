package com.microservicios.compras.controller.error;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un evento.
 * 
 * Esta clase extiende {@link RuntimeException} y proporciona mensajes de error
 * específicos para indicar que un evento no fue encontrado.
 * @author grupo1
 */
public class EventoNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EventoNotFoundException() {
        super("Evento no encontrado con ID: ");
    }

	public EventoNotFoundException(Long id) {
        super("Evento no encontrado con ID: " + id);
    }
}
