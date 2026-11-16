package com.example.taller.inventarioapi.domain.contracts;

import java.util.List;
import java.util.Optional;

import com.example.taller.inventarioapi.domain.aggregates.Categoria;

public interface ICategoriasRepositoryWR {
    Integer guardar(Categoria categoria);

    Optional<Categoria> buscarPorId(Integer id);

    List<Categoria> listar();

    void actualizar(Categoria categoria);

    void eliminar(Integer id);
}
