package com.morning_blue.galactic_tournament.service;

import com.morning_blue.galactic_tournament.model.dto.CombateRequestDTO;
import com.morning_blue.galactic_tournament.model.dto.CombateResponseDTO;
import com.morning_blue.galactic_tournament.model.dto.EspecieDTO;
import com.morning_blue.galactic_tournament.model.dto.RankingDTO;
import com.morning_blue.galactic_tournament.model.entity.Combate;
import com.morning_blue.galactic_tournament.model.entity.Especie;
import com.morning_blue.galactic_tournament.repository.CombateRepository;
import com.morning_blue.galactic_tournament.repository.EspecieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TorneoService {

    private final EspecieRepository especieRepository;
    private final CombateRepository combateRepository;
    private final Random random = new Random();

    @Transactional
    public Especie registrarEspecie(EspecieDTO dto) {
        if (especieRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una especie con ese nombre");
        }

        Especie especie = new Especie();
        especie.setNombre(dto.getNombre());
        especie.setNivelPoder(dto.getNivelPoder());
        especie.setHabilidadEspecial(dto.getHabilidadEspecial());
        especie.setVictorias(0);
        especie.setDerrotas(0);

        return especieRepository.save(especie);
    }

    public List<Especie> listarEspecies() {
        return especieRepository.findAll();
    }

    public Especie obtenerEspecie(Long id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especie no encontrada"));
    }

    @Transactional
    public CombateResponseDTO iniciarCombate(CombateRequestDTO request) {
        if (request.getEspecie1Id().equals(request.getEspecie2Id())) {
            throw new IllegalArgumentException("Una especie no puede combatir consigo misma");
        }

        Especie especie1 = obtenerEspecie(request.getEspecie1Id());
        Especie especie2 = obtenerEspecie(request.getEspecie2Id());

        // Determinar ganador
        Especie ganador = determinarGanador(especie1, especie2);
        Especie perdedor = ganador.equals(especie1) ? especie2 : especie1;

        // Actualizar estadísticas
        ganador.incrementarVictorias();
        perdedor.incrementarDerrotas();

        especieRepository.save(ganador);
        especieRepository.save(perdedor);

        // Registrar combate
        String resultado = generarResultado(especie1, especie2, ganador);

        Combate combate = new Combate();
        combate.setEspecie1(especie1);
        combate.setEspecie2(especie2);
        combate.setGanador(ganador);
        combate.setResultado(resultado);
        combate.setFecha(LocalDateTime.now());

        combate = combateRepository.save(combate);

        return mapearCombateResponse(combate);
    }

    private Especie determinarGanador(Especie e1, Especie e2) {
        // Primero comparar por nivel de poder
        if (!e1.getNivelPoder().equals(e2.getNivelPoder())) {
            return e1.getNivelPoder() > e2.getNivelPoder() ? e1 : e2;
        }

        // En caso de empate, gana el nombre alfabéticamente primero
        return e1.getNombre().compareToIgnoreCase(e2.getNombre()) < 0 ? e1 : e2;
    }

    private String generarResultado(Especie e1, Especie e2, Especie ganador) {
        if (!e1.getNivelPoder().equals(e2.getNivelPoder())) {
            return String.format("%s venció a %s por superioridad de poder (%d vs %d)",
                    ganador.getNombre(),
                    ganador.equals(e1) ? e2.getNombre() : e1.getNombre(),
                    ganador.getNivelPoder(),
                    ganador.equals(e1) ? e2.getNivelPoder() : e1.getNivelPoder());
        } else {
            return String.format("%s venció a %s por ventaja alfabética (empate en poder: %d)",
                    ganador.getNombre(),
                    ganador.equals(e1) ? e2.getNombre() : e1.getNombre(),
                    e1.getNivelPoder());
        }
    }

    public List<RankingDTO> obtenerRanking() {
        List<Especie> especies = especieRepository.findAllByOrderByVictoriasDesc();

        return IntStream.range(0, especies.size())
                .mapToObj(i -> {
                    Especie e = especies.get(i);
                    RankingDTO dto = new RankingDTO();
                    dto.setId(e.getId());
                    dto.setNombre(e.getNombre());
                    dto.setNivelPoder(e.getNivelPoder());
                    dto.setHabilidadEspecial(e.getHabilidadEspecial());
                    dto.setVictorias(e.getVictorias());
                    dto.setDerrotas(e.getDerrotas());
                    dto.setPosicion(i + 1);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CombateResponseDTO> obtenerHistorialCombates() {
        return combateRepository.findAllByOrderByFechaDesc().stream()
                .map(this::mapearCombateResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CombateResponseDTO combateAleatorio() {
        List<Especie> especies = especieRepository.findAll();

        if (especies.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 especies para un combate");
        }

        Especie e1 = especies.get(random.nextInt(especies.size()));
        Especie e2;

        do {
            e2 = especies.get(random.nextInt(especies.size()));
        } while (e1.getId().equals(e2.getId()));

        CombateRequestDTO request = new CombateRequestDTO(e1.getId(), e2.getId());
        return iniciarCombate(request);
    }

    private CombateResponseDTO mapearCombateResponse(Combate combate) {
        CombateResponseDTO dto = new CombateResponseDTO();
        dto.setId(combate.getId());
        dto.setEspecie1(combate.getEspecie1().getNombre());
        dto.setEspecie2(combate.getEspecie2().getNombre());
        dto.setGanador(combate.getGanador().getNombre());
        dto.setResultado(combate.getResultado());
        dto.setFecha(combate.getFecha());
        return dto;
    }
}