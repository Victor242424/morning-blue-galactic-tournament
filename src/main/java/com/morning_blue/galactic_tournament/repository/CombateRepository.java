package com.morning_blue.galactic_tournament.repository;

import com.morning_blue.galactic_tournament.model.entity.Combate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CombateRepository extends JpaRepository<Combate, Long> {
    List<Combate> findAllByOrderByFechaDesc();
}