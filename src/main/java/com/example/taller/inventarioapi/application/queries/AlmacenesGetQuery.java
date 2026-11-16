package com.example.taller.inventarioapi.application.queries;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenesGetQuery {
    @NotNull
    @Positive
    private Integer id;
}
