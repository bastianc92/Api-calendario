package com.itm.apicalendario.controller;

import com.itm.apicalendario.model.Festivo;
import com.itm.apicalendario.repository.FestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/festivos")
public class FestivoController {

    @Autowired
    private FestivoRepository festivoRepository;

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> verificarFestivo(@RequestParam LocalDate fecha) {
        Optional<Festivo> festivo = festivoRepository.findByFecha(fecha);
        boolean esFestivo = festivo.isPresent();
        return ResponseEntity.ok(esFestivo);
    }
}
