package com.trap.memo.repository;

import com.trap.memo.mapper.RoleMapper;
import com.trap.memo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JDBCRoleRepository implements RoleRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    @Transactional(readOnly = true)
    public Role getOne(Long id) {
        String sql = "SELECT * from roles where id = ?";
        Role role = jdbcTemplate.queryForObject(sql, new RoleMapper(), id);
        return role;
    }
}
