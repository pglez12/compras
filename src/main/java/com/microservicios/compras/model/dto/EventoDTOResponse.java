package com.microservicios.compras.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoDTOResponse {
		
		private Long id;
		
		private String nombre;
		
		private String descripcion;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
		private Date fechaevento;
		
		private Double preciomin;
		
		private Double preciomax;
		
		private String genero;
		
		private String localidad;
		
		private Boolean activo;
		
}
