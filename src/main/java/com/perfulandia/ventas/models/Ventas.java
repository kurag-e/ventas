package com.perfulandia.ventas.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@Data
public class Ventas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer clienteId;
    private Integer productoId;
    private Integer cantidad;
    private Double total;
    private LocalDateTime fechaVenta;
}
