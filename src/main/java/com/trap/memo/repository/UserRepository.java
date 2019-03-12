package com.trap.memo.repository;

import com.trap.memo.model.User;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
    User findById(Long id);
    boolean isSuchUserExist(String username);
}
