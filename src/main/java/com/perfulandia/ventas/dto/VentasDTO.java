package com.perfulandia.ventas.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VentasDTO extends RepresentationModel<VentasDTO> {
    private Integer id;
    private Integer Id_cliente;
    private Integer productoId;
    private Integer cantidad;
    private Double total;
    private LocalDateTime fechaVenta;
}
