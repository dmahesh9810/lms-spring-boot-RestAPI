package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}

