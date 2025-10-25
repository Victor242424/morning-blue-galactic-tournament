package com.morning_blue.galactic_tournament.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombateRequestDTO {
    @NotNull(message = "El ID de la primera especie es obligatorio")
    private Long especie1Id;

    @NotNull(message = "El ID de la segunda especie es obligatorio")
    private Long especie2Id;
}