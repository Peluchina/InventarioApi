package com.example.taller.inventarioapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.domain.aggregates.Producto;
import com.example.taller.inventarioapi.domain.contracts.IProductosRepositoryWR;
import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductoRepository implements IProductosRepositoryWR {
    private final IConnectionsMySql connection;

    @Override
    public Integer guardar(Producto producto) {
        String sql = "CALL sp_productos_crear(?, ?, ?, ?, ?, ?, ?, ?)";
        return connection.getJdbcTemplate().queryForObject(
                sql,
                Integer.class,
                producto.getSku(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getCategoriaId(),
                producto.getPrecioCompra(),
                producto.getPrecioVenta(),
                producto.getStockMinimo(),
                producto.getStockActual()
        );
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {
        String sql = "CALL sp_productos_obtener(?)";
        List<Producto> resultado = connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Producto.builder()
                .id(rs.getInt("id"))
                .sku(rs.getString("sku"))
                .nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion"))
                .categoriaId(rs.getObject("categoria_id", Integer.class))
                .categoria(rs.getString("categoria"))
                .precioCompra(rs.getBigDecimal("precio_compra"))
                .precioVenta(rs.getBigDecimal("precio_venta"))
                .stockMinimo(rs.getObject("stock_minimo", Integer.class))
                .stockActual(rs.getObject("stock_actual", Integer.class))
                .build(), id);
        return resultado.stream().findFirst();
    }

    @Override
    public List<Producto> listar() {
        String sql = "CALL sp_productos_listar()";
        return connection.getJdbcTemplate().query(sql, (rs, rowNum) -> Producto.builder()
                .id(rs.getInt("id"))
                .sku(rs.getString("sku"))
                .nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion"))
                .categoriaId(rs.getObject("categoria_id", Integer.class))
                .categoria(rs.getString("categoria"))
                .precioCompra(rs.getBigDecimal("precio_compra"))
                .precioVenta(rs.getBigDecimal("precio_venta"))
                .stockMinimo(rs.getObject("stock_minimo", Integer.class))
                .stockActual(rs.getObject("stock_actual", Integer.class))
                .estadoId(rs.getInt("estado_id"))
                .estadoStock(rs.getString("estado_stock"))
                .build());
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "CALL sp_productos_actualizar(?, ?, ?, ?, ?, ?, ?, ?)";
        connection.getJdbcTemplate().update(
                sql,
                producto.getId(),
                producto.getSku(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getCategoriaId(),
                producto.getPrecioCompra(),
                producto.getPrecioVenta(),
                producto.getStockMinimo()
        );
    }

    @Override
    public void actualizarEstado(Integer id, Integer estadoId) {
        String sql = "CALL sp_productos_actualizar_estado(?, ?)";
        connection.getJdbcTemplate().update(sql, id, estadoId);
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "CALL sp_productos_eliminar(?)";
        connection.getJdbcTemplate().update(sql, id);
    }
}
