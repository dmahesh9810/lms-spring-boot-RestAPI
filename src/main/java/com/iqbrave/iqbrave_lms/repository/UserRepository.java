package com.iqbrave.iqbrave_lms.repository;


import com.iqbrave.iqbrave_lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

