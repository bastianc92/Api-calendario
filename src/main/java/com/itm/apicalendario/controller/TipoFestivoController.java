package com.itm.apicalendario.controller;

import com.itm.apicalendario.model.TipoFestivo;
import com.itm.apicalendario.repository.TipoFestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipofestivo")
public class TipoFestivoController {

    @Autowired
    private TipoFestivoRepository tipoFestivoRepository;

    @GetMapping
    public ResponseEntity<List<TipoFestivo>> listarTodos() {
        List<TipoFestivo> tiposFestivos = tipoFestivoRepository.findAll();
        return ResponseEntity.ok(tiposFestivos);
    }

    @PostMapping
    public ResponseEntity<TipoFestivo> crearTipoFestivo(@RequestBody TipoFestivo tipoFestivo) {
        TipoFestivo tipoGuardado = tipoFestivoRepository.save(tipoFestivo);
        return ResponseEntity.status(201).body(tipoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoFestivo> actualizarTipoFestivo(@PathVariable Long id,
            @RequestBody TipoFestivo tipoFestivo) {
        Optional<TipoFestivo> tipoExistente = tipoFestivoRepository.findById(id);

        if (tipoExistente.isPresent()) {
            TipoFestivo tipo = tipoExistente.get();
            tipo.setNombre(tipoFestivo.getNombre());
            tipo.setDescripcion(tipoFestivo.getDescripcion());
            tipo.setEsFeriado(tipoFestivo.getEsFeriado());

            TipoFestivo tipoActualizado = tipoFestivoRepository.save(tipo);
            return ResponseEntity.ok(tipoActualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoFestivo(@PathVariable Long id) {
        Optional<TipoFestivo> tipoExistente = tipoFestivoRepository.findById(id);

        if (tipoExistente.isPresent()) {
            tipoFestivoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
