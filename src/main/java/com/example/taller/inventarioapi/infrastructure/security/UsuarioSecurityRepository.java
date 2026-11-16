package com.example.taller.inventarioapi.infrastructure.security;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

@Repository 
public class UsuarioSecurityRepository {
    private final IConnectionsMySql connection;
    public UsuarioSecurityRepository(IConnectionsMySql connection) {
        this.connection = connection;
    }

    public Optional<UsuarioOAuth2> buscarPorEmail(String email) {

        String sql = "CALL sp_usuarios_por_email(?)";

        List<UsuarioOAuth2> usuarios = connection.getJdbcTemplate().query(
            sql,
            (rs, rowNum) -> mapearUsuario(rs),
            email
        );

        return usuarios.stream().findFirst();
    }

    private UsuarioOAuth2 mapearUsuario(ResultSet rs) throws SQLException {
        return new UsuarioOAuth2(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("email"),
            nullableInt(rs, "rol_id"),
            rs.getString("rol"),
            nullableInt(rs, "estado_id")
        );
    }

    private Integer nullableInt(ResultSet rs, String column) throws SQLException {
        int value = rs.getInt(column);
        return rs.wasNull() ? null : value;
    }
}
