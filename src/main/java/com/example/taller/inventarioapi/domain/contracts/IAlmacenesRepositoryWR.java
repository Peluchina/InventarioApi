package com.example.taller.inventarioapi.domain.contracts;

import java.util.List;
import java.util.Optional;

import com.example.taller.inventarioapi.domain.aggregates.Almacen;

public interface IAlmacenesRepositoryWR {
    Integer guardar(Almacen almacen);

    Optional<Almacen> buscarPorId(Integer id);

    List<Almacen> listar();

    void actualizar(Almacen almacen);

    void actualizarEstado(Integer id, Integer estadoId);

    void eliminar(Integer id);
}
