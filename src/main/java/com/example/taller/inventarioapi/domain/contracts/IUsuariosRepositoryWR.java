package com.example.taller.inventarioapi.domain.contracts;

import java.util.List;
import java.util.Optional;

import com.example.taller.inventarioapi.domain.aggregates.Usuario;

public interface IUsuariosRepositoryWR {
    Integer guardar(Usuario usuario);

    Optional<Usuario> buscarPorId(Integer id);

    List<Usuario> listar();

    void actualizar(Usuario usuario);

    void actualizarEstado(Integer id, Integer estadoId);

    void eliminar(Integer id);
}
