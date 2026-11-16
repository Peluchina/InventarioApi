package com.example.taller.inventarioapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.domain.aggregates.Categoria;
import com.example.taller.inventarioapi.domain.contracts.ICategoriasRepositoryWR;
import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoriaRepository implements ICategoriasRepositoryWR {
    private final IConnectionsMySql connection;

    @Override
    public Integer guardar(Categoria categoria) {
        String sql = "CALL sp_categorias_crear(?, ?)";
        return connection.getJdbcTemplate().queryForObject(
                sql,
                Integer.class,
                categoria.getNombre(),
                categoria.getDescripcion());
    }

    @Override
    public Optional<Categoria> buscarPorId(Integer id) {
        String sql = "CALL sp_categorias_obtener(?)";
        List<Categoria> resultado = connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Categoria.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion"))
                .build(), id);
        return resultado.stream().findFirst();
    }

    @Override
    public List<Categoria> listar() {
        String sql = "CALL sp_categorias_listar()";
        return connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Categoria.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion"))
                .build());
    }

    @Override
    public void actualizar(Categoria categoria) {
        String sql = "CALL sp_categorias_actualizar(?, ?, ?)";
        connection.getJdbcTemplate().update(sql, categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "CALL sp_categorias_eliminar(?)";
        connection.getJdbcTemplate().update(sql, id);
    }
}
