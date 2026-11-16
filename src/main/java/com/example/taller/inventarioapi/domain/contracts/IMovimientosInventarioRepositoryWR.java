package com.example.taller.inventarioapi.domain.contracts;

import java.util.List;
import java.util.Optional;

import com.example.taller.inventarioapi.domain.aggregates.MovimientoInventario;
import com.example.taller.inventarioapi.domain.aggregates.MovimientoRegistroResultado;

public interface IMovimientosInventarioRepositoryWR {
    MovimientoRegistroResultado registrar(MovimientoInventario movimiento);

    List<MovimientoInventario> listar();

    Optional<MovimientoInventario> buscarPorId(Integer id);

    List<MovimientoInventario> listarPorProducto(Integer productoId);
}
