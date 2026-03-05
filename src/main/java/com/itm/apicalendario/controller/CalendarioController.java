package com.itm.apicalendario.controller;

import com.itm.apicalendario.model.Calendario;
import com.itm.apicalendario.repository.CalendarioRepository;
import com.itm.apicalendario.repository.FestivoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private FestivoRepository festivoRepository;

    // ===============================
    // 1️⃣ Listar días laborales
    // ===============================
    @GetMapping("/laborales")
    public ResponseEntity<List<Calendario>> listarLaborales() {
        List<Calendario> laborales = calendarioRepository.findByEsLaboral(true);
        return ResponseEntity.ok(laborales);
    }

    // ===============================
    // 2️⃣ Listar días no laborales
    // ===============================
    @GetMapping("/no-laborales")
    public ResponseEntity<List<Calendario>> listarNoLaborales() {
        List<Calendario> noLaborales = calendarioRepository.findByEsLaboral(false);
        return ResponseEntity.ok(noLaborales);
    }

    // ===============================
    // 3️⃣ Verificar si una fecha es festiva
    // Formato requerido por el PDF:
    // /api/calendario/verificar/{paisId}/{anio}/{mes}/{dia}
    // ===============================
    @GetMapping("/verificar/{paisId}/{anio}/{mes}/{dia}")
    public ResponseEntity<String> verificarFecha(
            @PathVariable Long paisId,
            @PathVariable int anio,
            @PathVariable int mes,
            @PathVariable int dia) {

        try {
            LocalDate fecha = LocalDate.of(anio, mes, dia);

            boolean esFestivo = festivoRepository
                    .existsByFechaAndPaisId(fecha, paisId);

            if (esFestivo) {
                return ResponseEntity.ok("Es festivo");
            } else {
                return ResponseEntity.ok("No es festivo");
            }

        } catch (DateTimeException e) {
            return ResponseEntity.ok("Fecha no válida");
        }
    }
}