package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(user.getEmail());
                    existing.setName(user.getName());
                    if (user.getPassword() != null && !user.getPassword().isBlank()) {
                        existing.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                    existing.setRole(user.getRole());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

