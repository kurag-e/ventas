package com.perfulandia.ventas.controller;

import com.perfulandia.ventas.dto.VentasDTO;
import com.perfulandia.ventas.services.VentasServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentasController {

    @Autowired
    private VentasServices service;

    @PostMapping
    public VentasDTO crear(@RequestBody VentasDTO dto) {
        VentasDTO creado = service.guardar(dto);
        creado.add(linkTo(methodOn(VentasController.class).obtenerHATEOAS(creado.getId())).withSelfRel());
        creado.add(linkTo(methodOn(VentasController.class).obtenerTodosHATEOAS()).withRel("todos"));
        return creado;
    }

    @GetMapping("/hateoas/{id}")
    public VentasDTO obtenerHATEOAS(@PathVariable Integer id) {
        Long idLong = id.longValue();
        VentasDTO dto = service.obtenerPorId(idLong).orElse(null);
        if (dto != null) {
            dto.add(linkTo(methodOn(VentasController.class).obtenerHATEOAS(id)).withSelfRel());
            dto.add(linkTo(methodOn(VentasController.class).obtenerTodosHATEOAS()).withRel("todos"));
            dto.add(linkTo(methodOn(VentasController.class).eliminar(id)).withRel("eliminar"));
        }
        return dto;
    }

    
    @GetMapping("/hateoas")
    public List<VentasDTO> obtenerTodosHATEOAS() {
        List<VentasDTO> lista = service.listar();
        for (VentasDTO dto : lista) {
            dto.add(linkTo(methodOn(VentasController.class).obtenerHATEOAS(dto.getId().intValue())).withSelfRel());
        }
        return lista;
    }

    @GetMapping("/{id}")
    public VentasDTO obtener(@PathVariable Integer id) {
        Long idLong = id.longValue();
        return service.obtenerPorId(idLong).orElse(null);
    }

    @PutMapping("/{id}")
    public VentasDTO actualizar(@PathVariable Integer id, @RequestBody VentasDTO dto) {
        Long idLong = id.longValue();
        VentasDTO actualizado = service.actualizar(idLong, dto).orElse(null);
        if (actualizado != null) {
            actualizado.add(linkTo(methodOn(VentasController.class).obtenerHATEOAS(id)).withSelfRel());
            actualizado.add(linkTo(methodOn(VentasController.class).obtenerTodosHATEOAS()).withRel("todos"));
        }
        return actualizado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Long idLong = id.longValue();
        boolean eliminado = service.eliminar(idLong);
        return eliminado
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
