package com.example.bank.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean userExists(String userId) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }
    public String findUserIdByUsername(String username) {
        String sql = "SELECT id FROM USERS WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
