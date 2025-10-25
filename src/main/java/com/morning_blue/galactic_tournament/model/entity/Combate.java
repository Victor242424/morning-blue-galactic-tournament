package com.morning_blue.galactic_tournament.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "combates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter // Force getters
@Setter // Force setters
public class Combate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "especie1_id", nullable = false)
    private Especie especie1;

    @ManyToOne
    @JoinColumn(name = "especie2_id", nullable = false)
    private Especie especie2;

    @ManyToOne
    @JoinColumn(name = "ganador_id", nullable = false)
    private Especie ganador;

    @Column(nullable = false)
    private String resultado;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}