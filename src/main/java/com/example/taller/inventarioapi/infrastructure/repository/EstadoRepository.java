package com.example.taller.inventarioapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.domain.aggregates.Estado;
import com.example.taller.inventarioapi.domain.contracts.IEstadosRespositoryWR;
import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EstadoRepository implements IEstadosRespositoryWR {
    private final IConnectionsMySql connection;

    @Override
    public Optional<Estado> buscarPorId(Integer id) {
        String sql = "SELECT id, nombre FROM estado WHERE id = ?";
        List<Estado> resultado = connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Estado.builder()
                .id(rs.getObject("id", Integer.class))
                .nombre(rs.getString("nombre"))
                .build(), id);
        return resultado.stream().findFirst();
    }

    @Override
    public List<Estado> listar() {
        String sql = "SELECT id, nombre FROM estado ORDER BY id";
        return connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Estado.builder()
                .id(rs.getObject("id", Integer.class))
                .nombre(rs.getString("nombre"))
                .build());
    }
}
