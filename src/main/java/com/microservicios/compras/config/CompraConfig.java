package com.microservicios.compras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
/**
 * Clase de configuración para la API de Eventos.
 * 
 * Esta clase utiliza Spring para definir un bean que configura la documentación
 * de la API utilizando OpenAPI (Swagger).
 * @author grupo1
 */
@Configuration
public class CompraConfig {
	@Bean
	public OpenAPI ComprasOpenAPI() {
	 return new OpenAPI()
                .info(new Info().title("Compras API")
                .description("Documentación de la API Compras")
                .version("v1.0")
                .license(new License().name("LICENSE").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("Descripcion del proyecto")
                .url("https://miproyecto.es"));
    }

}

