package com.perfulandia.ventas.services;


import com.perfulandia.ventas.dto.VentasDTO;
import com.perfulandia.ventas.models.Ventas;
import com.perfulandia.ventas.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentasServices {

    @Autowired
    private VentasRepository repository;

    public VentasDTO guardar(VentasDTO dto) {
        Ventas venta = toEntity(dto);
        Ventas saved = repository.save(venta);
        return toDTO(saved);
    }

    public List<VentasDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<VentasDTO> obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<VentasDTO> actualizar(Long id, VentasDTO dto) {
        return repository.findById(id).map(venta -> {
            venta.setId_cliente(dto.getId_cliente());
            venta.setProductoId(dto.getProductoId());
            venta.setCantidad(dto.getCantidad());
            venta.setTotal(dto.getTotal());
            venta.setFechaVenta(dto.getFechaVenta());
            return toDTO(repository.save(venta));
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private VentasDTO toDTO(Ventas venta) {
        VentasDTO dto = new VentasDTO();
        dto.setId(venta.getId());
        dto.setId_cliente(venta.getId_cliente());
        dto.setProductoId(venta.getProductoId());
        dto.setCantidad(venta.getCantidad());
        dto.setTotal(venta.getTotal());
        dto.setFechaVenta(venta.getFechaVenta());
        return dto;
    }

    private Ventas toEntity(VentasDTO dto) {
        Ventas venta = new Ventas();
        venta.setId(dto.getId());
        venta.setId_cliente(dto.getId_cliente());
        venta.setProductoId(dto.getProductoId());
        venta.setCantidad(dto.getCantidad());
        venta.setTotal(dto.getTotal());
        venta.setFechaVenta(dto.getFechaVenta());
        return venta;
    } 
}
