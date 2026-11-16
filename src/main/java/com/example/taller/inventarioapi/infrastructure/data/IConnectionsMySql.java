package com.example.taller.inventarioapi.infrastructure.data;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IConnectionsMySql {
    JdbcTemplate getJdbcTemplate();
}
