package com.morning_blue.galactic_tournament.repository;

import com.morning_blue.galactic_tournament.model.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {
    Optional<Especie> findByNombre(String nombre);
    List<Especie> findAllByOrderByVictoriasDesc();
    boolean existsByNombre(String nombre);
}
