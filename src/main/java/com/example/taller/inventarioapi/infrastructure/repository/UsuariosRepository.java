package com.example.taller.inventarioapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.domain.aggregates.Usuario;
import com.example.taller.inventarioapi.domain.contracts.IUsuariosRepositoryWR;
import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsuariosRepository implements IUsuariosRepositoryWR {
    private final IConnectionsMySql connection;

    @Override
    public Integer guardar(Usuario usuario) {
        String sql = "CALL sp_usuarios_crear(?, ?, ?, ?, ?, ?, ?)";
        return connection.getJdbcTemplate().queryForObject(
                sql,
                Integer.class,
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getDni(),
                usuario.getDireccion(),
                usuario.getRolId(),
                usuario.getEmail(),
                usuario.getContrasenia()
        );
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        String sql = "CALL sp_usuarios_obtener(?)";
        List<Usuario> resultado = connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Usuario.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .apellidos(rs.getString("apellidos"))
                .dni(rs.getBigDecimal("dni"))
                .direccion(rs.getString("direccion"))
                .rolId(rs.getObject("rol_id", Integer.class))
                .email(rs.getString("email"))
                .estadoId(rs.getInt("estado_id"))
                .build(), id);
        return resultado.stream().findFirst();
    }

    @Override
    public List<Usuario> listar() {
        String sql = "CALL sp_usuarios_listar()";
        return connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Usuario.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .apellidos(rs.getString("apellidos"))
                .dni(rs.getBigDecimal("dni"))
                .direccion(rs.getString("direccion"))
                .rolId(rs.getObject("rol_id", Integer.class))
                .email(rs.getString("email"))
                .estadoId(rs.getInt("estado_id"))
                .build());
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "CALL sp_usuarios_actualizar(?, ?, ?, ?, ?, ?, ?, ?)";
        connection.getJdbcTemplate().update(
                sql,
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getDni(),
                usuario.getDireccion(),
                usuario.getRolId(),
                usuario.getEmail(),
                usuario.getContrasenia()
        );
    }

    @Override
    public void actualizarEstado(Integer id, Integer estadoId) {
        String sql = "CALL sp_usuarios_actualizarEstado(?, ?)";
        connection.getJdbcTemplate().update(sql, id, estadoId);
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "CALL sp_usuarios_eliminar(?)";
        connection.getJdbcTemplate().update(sql, id);
    }
}
