package com.example.taller.inventarioapi.application.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenesUpdateStatusRequest {
    @NotNull
    private Integer estadoId;
}
