package com.perfulandia.ventas.controller;

import com.perfulandia.ventas.dto.VentasDTO;
import com.perfulandia.ventas.services.VentasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    @Autowired
    private VentasServices service;

    @PostMapping
    public ResponseEntity<VentasDTO> crear(@RequestBody VentasDTO dto) {
        VentasDTO creada = service.guardar(dto);

        creada.add(linkTo(methodOn(VentasController.class).obtener(creada.getId().intValue())).withSelfRel());
        creada.add(linkTo(methodOn(VentasController.class).listar()).withRel("todas-las-ventas"));

        return ResponseEntity.ok(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasDTO> obtener(@PathVariable Integer id) {
        Long idInteger = id.longValue();
        return service.obtenerPorId(idInteger)
                .map(v -> {
                    v.add(linkTo(methodOn(VentasController.class).obtener(id)).withSelfRel());
                    v.add(linkTo(methodOn(VentasController.class).listar()).withRel("todas-las-ventas"));
                    return ResponseEntity.ok(v);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<CollectionModel<VentasDTO>> listar() {
        List<VentasDTO> lista = service.listar();

        lista.forEach(v ->
            v.add(linkTo(methodOn(VentasController.class).obtener(v.getId().intValue())).withSelfRel())
        );

        CollectionModel<VentasDTO> modelo = CollectionModel.of(lista);
        modelo.add(linkTo(methodOn(VentasController.class).listar()).withSelfRel());

        return ResponseEntity.ok(modelo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasDTO> actualizar(@PathVariable Integer id, @RequestBody VentasDTO dto) {
        Long idLong = id.longValue();
        return service.actualizar(idLong, dto)
                .map(v -> {
                    v.add(linkTo(methodOn(VentasController.class).obtener(id)).withSelfRel());
                    v.add(linkTo(methodOn(VentasController.class).listar()).withRel("todas-las-ventas"));
                    return ResponseEntity.ok(v);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Long idLong = id.longValue();
        boolean eliminada = service.eliminar(idLong);
        return eliminada
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
