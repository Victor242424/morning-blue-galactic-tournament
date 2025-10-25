package com.morning_blue.galactic_tournament.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private Long id;
    private String nombre;
    private Integer nivelPoder;
    private String habilidadEspecial;
    private Integer victorias;
    private Integer derrotas;
    private Integer posicion;
}