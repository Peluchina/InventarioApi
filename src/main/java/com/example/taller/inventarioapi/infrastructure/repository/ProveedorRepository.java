package com.example.taller.inventarioapi.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.domain.aggregates.Proveedor;
import com.example.taller.inventarioapi.domain.contracts.IProveedoresRepositoryWR;
import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

import lombok.RequiredArgsConstructor;

@Repository 
@RequiredArgsConstructor 
public class ProveedorRepository implements IProveedoresRepositoryWR{
    private final IConnectionsMySql connection;

    @Override
    public void actualizar(Proveedor proveedor) {
        String sql = """
            CALL sp_proveedores_actualizar(?, ?, ?, ?, ?,?)
            """;

            connection.getJdbcTemplate().update(
                    sql,
                    proveedor.getId(),
                    proveedor.getNombre(),
                    proveedor.getContacto(),
                    proveedor.getTelefono(),
                    proveedor.getEmail(),
                    proveedor.getDireccion()
            ); 
    }

    @Override
    public void actualizarEstado(Integer id, Integer estadoId) {
        String sql = "CALL sp_proveedores_actualizarEstado(?, ?)";
        connection.getJdbcTemplate().update(sql, id, estadoId);
    }

    @Override
    public Optional<Proveedor> buscarPorId(Integer id) {
        String sql = "CALL sp_proveedores_obtener(?)";

        List<Proveedor> resultado = connection.getJdbcTemplate().query(
                sql,
                (rs, rowNum) -> Proveedor.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .contacto(rs.getString("contacto"))
                        .telefono(rs.getString("telefono"))
                        .email(rs.getString("email"))
                        .direccion(rs.getString("direccion"))
                        .estadoId(rs.getInt("estado_id"))
                        .build(),
                id
        );
        return resultado.stream().findFirst();
    }

    @Override
    public void eliminar(Integer id) {   
        String sql = "CALL sp_proveedores_eliminar(?)";
        connection.getJdbcTemplate().update(sql, id);    
    }

    @Override
    public Integer guardar(Proveedor proveedor) {
        String sql = """
            CALL sp_proveedores_crear(?, ?, ?, ?, ?)
            """;

            return connection.getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class,
                    proveedor.getNombre(),
                    proveedor.getContacto(),
                    proveedor.getTelefono(),
                    proveedor.getEmail(),
                    proveedor.getDireccion()
            );
    }

    @Override
    public List<Proveedor> listar() {
        String sql = "CALL sp_proveedores_listar()";

        return connection.getJdbcTemplate().query(sql, (rs, rowNum) ->
                Proveedor.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .contacto(rs.getString("contacto"))
                        .telefono(rs.getString("telefono"))
                        .email(rs.getString("email"))
                        .direccion(rs.getString("direccion"))
                        .estadoId(rs.getInt("estado_id"))
                        .build()
        );
    }
}
