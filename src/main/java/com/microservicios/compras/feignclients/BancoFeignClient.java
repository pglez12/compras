package com.microservicios.compras.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.microservicios.compras.model.dto.BancoDTORequest;

@FeignClient(name = "bancoClient", url = "http://banco.eu-west-3.elasticbeanstalk.com")
public interface BancoFeignClient {
    /**
     * Realiza una compra a través de la pasarela de pago del Banco.
     *
     * @param request Objeto que contiene los datos de la compra.
     * @return ResponseEntity con la respuesta del servicio de Banco.
     */
    @PostMapping("/pasarela/compra")
    ResponseEntity<?> realizarCompra(BancoDTORequest request);
}
