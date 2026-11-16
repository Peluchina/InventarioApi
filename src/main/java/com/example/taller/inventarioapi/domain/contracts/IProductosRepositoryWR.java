package com.example.taller.inventarioapi.domain.contracts;

import java.util.List;
import java.util.Optional;

import com.example.taller.inventarioapi.domain.aggregates.Producto;

public interface IProductosRepositoryWR {
    Integer guardar(Producto producto);

    Optional<Producto> buscarPorId(Integer id);

    List<Producto> listar();

    void actualizar(Producto producto);

    void actualizarEstado(Integer id, Integer estadoId);

    void eliminar(Integer id);
}
