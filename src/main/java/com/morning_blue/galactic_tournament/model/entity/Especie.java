package com.morning_blue.galactic_tournament.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, unique = true)
    private String nombre;

    @Min(value = 0, message = "El nivel de poder debe ser positivo")
    @Column(nullable = false)
    private Integer nivelPoder;

    @NotBlank(message = "La habilidad especial es obligatoria")
    @Column(nullable = false, length = 500)
    private String habilidadEspecial;

    @Column(nullable = false)
    private Integer victorias = 0;

    @Column(nullable = false)
    private Integer derrotas = 0;

    public void incrementarVictorias() {
        this.victorias++;
    }

    public void incrementarDerrotas() {
        this.derrotas++;
    }
}