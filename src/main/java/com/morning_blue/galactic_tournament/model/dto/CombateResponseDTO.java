package com.morning_blue.galactic_tournament.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombateResponseDTO {
    private Long id;
    private String especie1;
    private String especie2;
    private String ganador;
    private String resultado;
    private LocalDateTime fecha;
}