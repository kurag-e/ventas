package com.perfulandia.ventas.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentasDTO extends RepresentationModel<VentasDTO> {
    private Integer id;
    private Integer idCliente;
    private Integer productoId;
    private Integer cantidad;
    private Double total;
    private LocalDateTime fechaVenta;
}
