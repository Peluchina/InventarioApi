package com.example.taller.inventarioapi.infrastructure.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConnectionsMySql implements IConnectionsMySql {
    private final JdbcTemplate jdbcTemplate;

    public ConnectionsMySql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
