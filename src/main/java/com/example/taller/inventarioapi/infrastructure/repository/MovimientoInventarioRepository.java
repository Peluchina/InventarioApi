package com.example.taller.inventarioapi.infrastructure.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.domain.aggregates.MovimientoInventario;
import com.example.taller.inventarioapi.domain.aggregates.MovimientoRegistroResultado;
import com.example.taller.inventarioapi.domain.contracts.IMovimientosInventarioRepositoryWR;
import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MovimientoInventarioRepository implements IMovimientosInventarioRepositoryWR {
    private final IConnectionsMySql connection;

    @Override
    public MovimientoRegistroResultado registrar(MovimientoInventario movimiento) {
        String sql = "CALL sp_movimientos_registrar(?, ?, ?, ?, ?)";
        return connection.getJdbcTemplate().queryForObject(
                sql,
                (rs, rowNum) -> new MovimientoRegistroResultado(
                        rs.getObject("movimiento_id", Integer.class),
                        rs.getObject("producto_id", Integer.class),
                        rs.getString("tipo_movimiento"),
                        rs.getObject("stock_anterior", Integer.class),
                        rs.getObject("stock_nuevo", Integer.class),
                        rs.getString("mensaje")),
                movimiento.getProductoId(),
                movimiento.getTipoMovimiento(),
                movimiento.getCantidad(),
                movimiento.getReferencia(),
                movimiento.getUsuarioId()
        );
    }

    @Override
    public List<MovimientoInventario> listar() {
        return connection.getJdbcTemplate().query("CALL sp_movimientos_listar()", (rs, rowNum) -> mapMovimiento(rs));
    }

    @Override
    public Optional<MovimientoInventario> buscarPorId(Integer id) {
        List<MovimientoInventario> resultados = connection.getJdbcTemplate().query(
                "CALL sp_movimientos_obtener(?)",
                (rs, rowNum) -> mapMovimiento(rs),
                id);
        return resultados.stream().findFirst();
    }

    @Override
    public List<MovimientoInventario> listarPorProducto(Integer productoId) {
        return connection.getJdbcTemplate().query(
                "CALL sp_movimientos_por_producto(?)",
                (rs, rowNum) -> mapMovimientoPorProducto(rs),
                productoId);
    }

    private MovimientoInventario mapMovimiento(ResultSet rs) throws SQLException {
        return MovimientoInventario.builder()
                .id(rs.getInt("id"))
                .productoId(rs.getObject("producto_id", Integer.class))
                .producto(rs.getString("producto"))
                .sku(rs.getString("sku"))
                .tipoMovimiento(rs.getString("tipo_movimiento"))
                .cantidad(rs.getObject("cantidad", Integer.class))
                .stockAnterior(rs.getObject("stock_anterior", Integer.class))
                .stockNuevo(rs.getObject("stock_nuevo", Integer.class))
                .referencia(rs.getString("referencia"))
                .usuarioId(rs.getObject("usuario_id", Integer.class))
                .usuario(rs.getString("usuario"))
                .fecha(toLocalTime(rs.getTime("fecha")))
                .createdAt(toLocalDateTime(rs.getTimestamp("created_at")))
                .updateAt(toLocalDateTime(rs.getTimestamp("update_at")))
                .build();
    }

    private MovimientoInventario mapMovimientoPorProducto(ResultSet rs) throws SQLException {
        return MovimientoInventario.builder()
                .id(rs.getInt("id"))
                .productoId(rs.getObject("producto_id", Integer.class))
                .producto(rs.getString("producto"))
                .tipoMovimiento(rs.getString("tipo_movimiento"))
                .cantidad(rs.getObject("cantidad", Integer.class))
                .stockAnterior(rs.getObject("stock_anterior", Integer.class))
                .stockNuevo(rs.getObject("stock_nuevo", Integer.class))
                .referencia(rs.getString("referencia"))
                .usuario(rs.getString("usuario"))
                .fecha(toLocalTime(rs.getTime("fecha")))
                .createdAt(toLocalDateTime(rs.getTimestamp("created_at")))
                .build();
    }

    private java.time.LocalTime toLocalTime(Time value) {
        return value == null ? null : value.toLocalTime();
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp value) {
        return value == null ? null : value.toLocalDateTime();
    }
}
