package com.example.taller.inventarioapi.domain.contracts;

import java.util.List;
import java.util.Optional;

import com.example.taller.inventarioapi.domain.aggregates.Proveedor;

public interface IProveedoresRepositoryWR {
    
    //Commands
    Integer guardar(Proveedor proveedor);

    void actualizar(Proveedor proveedor);

    void actualizarEstado(Integer id, Integer estadoId);

    void eliminar(Integer id);

    //Queries
    Optional<Proveedor> buscarPorId(Integer id);

    List<Proveedor> listar();
} 
