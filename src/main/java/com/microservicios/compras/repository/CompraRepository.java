package com.microservicios.compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.compras.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}
