package com.itm.apicalendario.controller;

import com.itm.apicalendario.model.Tipo;
import com.itm.apicalendario.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipo")
public class TipoController {

    @Autowired
    private TipoRepository tipoRepository;

    @GetMapping
    public ResponseEntity<List<Tipo>> listarTodos() {
        List<Tipo> tipos = tipoRepository.findAll();
        return ResponseEntity.ok(tipos);
    }

    @PostMapping
    public ResponseEntity<Tipo> crearTipo(@RequestBody Tipo tipo) {
        Tipo tipoGuardado = tipoRepository.save(tipo);
        return ResponseEntity.status(201).body(tipoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> actualizarTipo(@PathVariable Long id, @RequestBody Tipo tipo) {
        Optional<Tipo> tipoExistente = tipoRepository.findById(id);

        if (tipoExistente.isPresent()) {
            Tipo tipoActual = tipoExistente.get();
            tipoActual.setNombre(tipo.getNombre());
            tipoActual.setDescripcion(tipo.getDescripcion());
            tipoActual.setCodigo(tipo.getCodigo());

            Tipo tipoActualizado = tipoRepository.save(tipoActual);
            return ResponseEntity.ok(tipoActualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipo(@PathVariable Long id) {
        Optional<Tipo> tipoExistente = tipoRepository.findById(id);

        if (tipoExistente.isPresent()) {
            tipoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
