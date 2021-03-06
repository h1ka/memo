package com.trap.memo.repository;

import com.trap.memo.mapper.RoleMapper;
import com.trap.memo.mapper.UserMapper;
import com.trap.memo.model.Role;
import com.trap.memo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
@Repository
public class JDBCUserRepository implements UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        String sql = "SELECT * from users where username=?";
//        String selectRoles = "select  role_id from user_roles where user_roles.user_id=";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
        return user;
    }
    @Transactional(readOnly = true)
    public boolean isSuchUserExist(String username){
        String sql = "select count(*) from users where username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (count==0){
            return false;
        }
        else
            return true;
    }

    @Override
    @Transactional
    public void save(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        String sqlRole = "INSERT INTO user_roles(user_id, role_id) VALUES (?, ?)";
        String selectId = "SELECT * from users where username=?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
        User selectedUser = jdbcTemplate.queryForObject(selectId, new UserMapper(), user.getUsername());
        Long userId = selectedUser.getId();
        Set<Role> roles = user.getRoles();
        for (Role role:roles){
            Long roleId = role.getId();
            jdbcTemplate.update(sqlRole,userId,roleId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        String sql = "SELECT * FROM users where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        return user;
    }
}
