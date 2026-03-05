package com.itm.apicalendario.controller;

import com.itm.apicalendario.model.Pais;
import com.itm.apicalendario.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/pais")
public class PaisController {
    
    @Autowired
    private PaisRepository paisRepository;
    
    @GetMapping
    public ResponseEntity<List<Pais>> listarPaises() {
        List<Pais> paises = paisRepository.findAll();
        return ResponseEntity.ok(paises);
    }
}
