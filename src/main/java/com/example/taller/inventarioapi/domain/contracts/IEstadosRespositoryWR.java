package com.example.taller.inventarioapi.domain.contracts;

import java.util.List;
import java.util.Optional;

import com.example.taller.inventarioapi.domain.aggregates.Estado;

public interface IEstadosRespositoryWR {
    
    Optional<Estado> buscarPorId(Integer id);

    List<Estado> listar();
}
