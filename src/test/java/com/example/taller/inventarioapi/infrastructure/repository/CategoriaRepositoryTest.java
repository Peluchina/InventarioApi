package com.example.taller.inventarioapi.infrastructure.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.taller.inventarioapi.infrastructure.data.IConnectionsMySql;

class CategoriaRepositoryTest {
    @Test
    void eliminarUsaLaConexionInyectadaYConservaElProcedimientoAlmacenado() {
        IConnectionsMySql connection = mock(IConnectionsMySql.class);
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        when(connection.getJdbcTemplate()).thenReturn(jdbcTemplate);
        CategoriaRepository repository = new CategoriaRepository(connection);

        repository.eliminar(5);

        verify(jdbcTemplate).update("CALL sp_categorias_eliminar(?)", 5);
    }
}
