package com.morning_blue.galactic_tournament.controller;

import com.morning_blue.galactic_tournament.model.dto.CombateRequestDTO;
import com.morning_blue.galactic_tournament.model.dto.CombateResponseDTO;
import com.morning_blue.galactic_tournament.model.dto.EspecieDTO;
import com.morning_blue.galactic_tournament.model.dto.RankingDTO;
import com.morning_blue.galactic_tournament.model.entity.Especie;
import com.morning_blue.galactic_tournament.service.TorneoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TorneoController {

    private final TorneoService torneoService;

    @PostMapping("/especies")
    public ResponseEntity<Especie> registrarEspecie(@Valid @RequestBody EspecieDTO especieDTO) {
        try {
            Especie especie = torneoService.registrarEspecie(especieDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(especie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/especies")
    public ResponseEntity<List<Especie>> listarEspecies() {
        return ResponseEntity.ok(torneoService.listarEspecies());
    }

    @GetMapping("/especies/{id}")
    public ResponseEntity<Especie> obtenerEspecie(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(torneoService.obtenerEspecie(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/combates")
    public ResponseEntity<CombateResponseDTO> iniciarCombate(@Valid @RequestBody CombateRequestDTO request) {
        try {
            CombateResponseDTO combate = torneoService.iniciarCombate(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(combate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/combates")
    public ResponseEntity<List<CombateResponseDTO>> listarCombates() {
        return ResponseEntity.ok(torneoService.obtenerHistorialCombates());
    }

    @PostMapping("/combates/aleatorio")
    public ResponseEntity<CombateResponseDTO> combateAleatorio() {
        try {
            CombateResponseDTO combate = torneoService.combateAleatorio();
            return ResponseEntity.status(HttpStatus.CREATED).body(combate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingDTO>> obtenerRanking() {
        return ResponseEntity.ok(torneoService.obtenerRanking());
    }
}