package com.trap.memo.service;

import com.trap.memo.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    boolean isSuchUserExist(String username);
}
