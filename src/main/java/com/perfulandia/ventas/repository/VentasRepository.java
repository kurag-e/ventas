package com.perfulandia.ventas.repository;

import com.perfulandia.ventas.models.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentasRepository extends JpaRepository<Ventas, Long> {
}
