package com.microservicios.compras.controller.error;

public class EventoNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EventoNotFoundException() {
        super("Evento no encontrado con ID: ");
    }

	public EventoNotFoundException(Long id) {
        super("Evento no encontrado con ID: " + id);
    }
}
