package com.platzi_play.domain.dto;

import com.platzi_play.domain.Genre;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record MovieDto(
        Long id,

        @NotBlank(message = "El título es obligatorio")
        String title,

        Integer duration,
        Genre genre,

        @PastOrPresent(message = "La fecha de lanzamiento debe ser anterior a la fecha actual ")
        LocalDate releaseDate,

        @Min(value = 0, message = "El rating no puede ser menor que 0")
        @Max(value = 5, message = "El rating no puede ser mayor que 5")
        Double rating,

        boolean status
) {
}
