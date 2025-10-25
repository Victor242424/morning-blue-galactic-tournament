package com.morning_blue.galactic_tournament.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecieDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(value = 0, message = "El nivel de poder debe ser positivo")
    private Integer nivelPoder;

    @NotBlank(message = "La habilidad especial es obligatoria")
    private String habilidadEspecial;
}