package com.example.taller.inventarioapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.domain.aggregates.Almacen;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;
import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlmacenRepository implements IAlmacenesRepositoryWR {
    private final IConnectionsMySql connection;

    @Override
    public Integer guardar(Almacen almacen) {
        String sql = "CALL sp_almacenes_crear(?, ?)";
        return connection.getJdbcTemplate().queryForObject(
                sql,
                Integer.class,
                almacen.getNombre(),
                almacen.getUbicacion()
        );
    }

    @Override
    public Optional<Almacen> buscarPorId(Integer id) {
        String sql = "CALL sp_almacenes_obtener(?)";
        List<Almacen> resultado = connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Almacen.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .ubicacion(rs.getString("ubicacion"))
                .estadoId(rs.getInt("estado_id"))
                .build(), id);
        return resultado.stream().findFirst();
    }

    @Override
    public List<Almacen> listar() {
        String sql = "CALL sp_almacenes_listar()";
        return connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Almacen.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .ubicacion(rs.getString("ubicacion"))
                .estadoId(rs.getInt("estado_id"))
                .build());
    }

    @Override
    public void actualizar(Almacen almacen) {
        String sql = "CALL sp_almacenes_actualizar(?, ?, ?)";
        connection.getJdbcTemplate().update(sql, almacen.getId(), almacen.getNombre(), almacen.getUbicacion());
    }

    @Override
    public void actualizarEstado(Integer id, Integer estadoId) {
        String sql = "CALL sp_almacenes_actualizarEstado(?, ?)";
        connection.getJdbcTemplate().update(sql, id, estadoId);
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "CALL sp_almacenes_eliminar(?)";
        connection.getJdbcTemplate().update(sql, id);
    }
}
