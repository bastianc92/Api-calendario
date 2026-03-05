package com.itm.apicalendario.controller;

import com.itm.apicalendario.model.Calendario;
import com.itm.apicalendario.model.Festivo;
import com.itm.apicalendario.model.Pais;
import com.itm.apicalendario.model.Tipo;
import com.itm.apicalendario.repository.CalendarioRepository;
import com.itm.apicalendario.repository.FestivoRepository;
import com.itm.apicalendario.repository.PaisRepository;
import com.itm.apicalendario.repository.TipoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private FestivoRepository festivoRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private TipoRepository tipoRepository;

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

    // ===============================
    // 4️⃣ Listar festivos de un país en un año
    // GET /api/calendario/festivos/{paisId}/{anio}
    // ===============================
    @GetMapping("/festivos/{paisId}/{anio}")
    public ResponseEntity<List<Map<String, String>>> listarFestivos(
            @PathVariable Long paisId,
            @PathVariable int anio) {

        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);

        List<Festivo> festivos = festivoRepository.findByPaisIdAndFechaBetween(paisId, inicio, fin);

        List<Map<String, String>> resultado = festivos.stream()
                .map(f -> Map.of(
                        "nombre", f.getNombre(),
                        "fecha", f.getFecha().toString()))
                .toList();

        return ResponseEntity.ok(resultado);
    }

    // ===============================
    // 5️⃣ Generar calendario completo
    // GET /api/calendario/generar/{paisId}/{anio}
    // ===============================
    @GetMapping("/generar/{paisId}/{anio}")
    public ResponseEntity<Boolean> generarCalendario(
            @PathVariable Long paisId,
            @PathVariable int anio) {

        Optional<Pais> paisOpt = paisRepository.findById(paisId);
        if (paisOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Pais pais = paisOpt.get();

        Optional<Tipo> tipoLaboral = tipoRepository.findByNombre("Laboral");
        Optional<Tipo> tipoFestivo = tipoRepository.findByNombre("Festivo");
        Optional<Tipo> tipoFinSemana = tipoRepository.findByNombre("Fin de semana");

        if (tipoLaboral.isEmpty() || tipoFestivo.isEmpty() || tipoFinSemana.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);

        for (LocalDate fecha = inicio; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {
            Calendario calendario = new Calendario();
            calendario.setFecha(fecha);
            calendario.setPais(pais);

            if (festivoRepository.existsByFechaAndPaisId(fecha, paisId)) {
                calendario.setEsLaboral(false);
                calendario.setObservaciones("Día festivo");
                calendario.setTipo(tipoFestivo.get());
            } else {
                DayOfWeek diaSemana = fecha.getDayOfWeek();
                if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
                    calendario.setEsLaboral(false);
                    calendario.setObservaciones("Fin de semana");
                    calendario.setTipo(tipoFinSemana.get());
                } else {
                    calendario.setEsLaboral(true);
                    calendario.setObservaciones("Día laboral");
                    calendario.setTipo(tipoLaboral.get());
                }
            }

            calendarioRepository.save(calendario);
        }

        return ResponseEntity.ok(true);
    }

    // ===============================
    // 6️⃣ Listar calendario completo con clasificación
    // GET /api/calendario/listar/{paisId}/{anio}
    // ===============================
    @GetMapping("/listar/{paisId}/{anio}")
    public ResponseEntity<List<Map<String, String>>> listarCalendario(
            @PathVariable Long paisId,
            @PathVariable int anio) {

        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);

        List<Calendario> calendarios = calendarioRepository.findByPaisIdAndFechaBetween(paisId, inicio, fin);

        List<Map<String, String>> resultado = calendarios.stream()
                .map(c -> Map.of(
                        "fecha", c.getFecha().toString(),
                        "tipo", c.getTipo().getNombre(),
                        "observaciones", c.getObservaciones()))
                .toList();

        return ResponseEntity.ok(resultado);
    }
}