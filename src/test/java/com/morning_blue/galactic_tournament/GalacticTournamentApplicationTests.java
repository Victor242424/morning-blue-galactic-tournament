package com.morning_blue.galactic_tournament;

import com.morning_blue.galactic_tournament.model.dto.CombateRequestDTO;
import com.morning_blue.galactic_tournament.model.dto.CombateResponseDTO;
import com.morning_blue.galactic_tournament.model.dto.EspecieDTO;
import com.morning_blue.galactic_tournament.model.dto.RankingDTO;
import com.morning_blue.galactic_tournament.model.entity.Especie;
import com.morning_blue.galactic_tournament.repository.CombateRepository;
import com.morning_blue.galactic_tournament.repository.EspecieRepository;
import com.morning_blue.galactic_tournament.service.TorneoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class GalacticTournamentApplicationTests {


    @Autowired
    private TorneoService torneoService;

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private CombateRepository combateRepository;

    @BeforeEach
    void setUp() {
        combateRepository.deleteAll();
        especieRepository.deleteAll();
    }

    @Test
    void testRegistrarEspecie() {
        EspecieDTO dto = new EspecieDTO("Kryptoniano", 9500, "Superfuerza");
        Especie especie = torneoService.registrarEspecie(dto);

        assertNotNull(especie.getId());
        assertEquals("Kryptoniano", especie.getNombre());
        assertEquals(9500, especie.getNivelPoder());
        assertEquals(0, especie.getVictorias());
    }

    @Test
    void testRegistrarEspecieDuplicada() {
        EspecieDTO dto = new EspecieDTO("Saiyan", 9000, "Transformación");
        torneoService.registrarEspecie(dto);

        assertThrows(IllegalArgumentException.class, () -> {
            torneoService.registrarEspecie(dto);
        });
    }

    @Test
    void testCombateGanaPorPoder() {
        Especie e1 = torneoService.registrarEspecie(
                new EspecieDTO("Especie A", 1000, "Habilidad A"));
        Especie e2 = torneoService.registrarEspecie(
                new EspecieDTO("Especie B", 500, "Habilidad B"));

        CombateRequestDTO request = new CombateRequestDTO(e1.getId(), e2.getId());
        CombateResponseDTO resultado = torneoService.iniciarCombate(request);

        assertEquals("Especie A", resultado.getGanador());
        assertTrue(resultado.getResultado().contains("superioridad de poder"));

        Especie ganador = especieRepository.findById(e1.getId()).get();
        assertEquals(1, ganador.getVictorias());
    }

    @Test
    void testCombateEmpateGanaAlfabetico() {
        Especie e1 = torneoService.registrarEspecie(
                new EspecieDTO("Zebra", 1000, "Habilidad Z"));
        Especie e2 = torneoService.registrarEspecie(
                new EspecieDTO("Alpha", 1000, "Habilidad A"));

        CombateRequestDTO request = new CombateRequestDTO(e1.getId(), e2.getId());
        CombateResponseDTO resultado = torneoService.iniciarCombate(request);

        assertEquals("Alpha", resultado.getGanador());
        assertTrue(resultado.getResultado().contains("ventaja alfabética"));
    }

    @Test
    void testRanking() {
        Especie e1 = torneoService.registrarEspecie(
                new EspecieDTO("Primero", 1000, "Habilidad 1"));
        Especie e2 = torneoService.registrarEspecie(
                new EspecieDTO("Segundo", 500, "Habilidad 2"));
        Especie e3 = torneoService.registrarEspecie(
                new EspecieDTO("Tercero", 250, "Habilidad 3"));

        // Realizar combates
        torneoService.iniciarCombate(new CombateRequestDTO(e1.getId(), e2.getId()));
        torneoService.iniciarCombate(new CombateRequestDTO(e1.getId(), e3.getId()));

        List<RankingDTO> ranking = torneoService.obtenerRanking();

        assertEquals(3, ranking.size());
        assertEquals("Primero", ranking.get(0).getNombre());
        assertEquals(2, ranking.get(0).getVictorias());
        assertEquals(1, ranking.get(0).getPosicion());
    }

    @Test
    void testCombateAleatorio() {
        torneoService.registrarEspecie(new EspecieDTO("Especie 1", 100, "Hab 1"));
        torneoService.registrarEspecie(new EspecieDTO("Especie 2", 200, "Hab 2"));
        torneoService.registrarEspecie(new EspecieDTO("Especie 3", 300, "Hab 3"));

        CombateResponseDTO resultado = torneoService.combateAleatorio();

        assertNotNull(resultado);
        assertNotNull(resultado.getGanador());
        assertNotEquals(resultado.getEspecie1(), resultado.getEspecie2());
    }

    @Test
    void testCombateConMismaEspecie() {
        Especie e1 = torneoService.registrarEspecie(
                new EspecieDTO("Especie", 100, "Habilidad"));

        CombateRequestDTO request = new CombateRequestDTO(e1.getId(), e1.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            torneoService.iniciarCombate(request);
        });
    }

}
