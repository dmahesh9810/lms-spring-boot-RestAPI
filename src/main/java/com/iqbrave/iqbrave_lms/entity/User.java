package com.iqbrave.iqbrave_lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // Use your concrete Role enum here

    // ... existing code ...
    // Remove the multi-role mapping to match the single-role model and the constructor used in DatabaseSeeder

    // @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    // @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    // @Enumerated(EnumType.STRING)
    // @Column(name = "role", nullable = false)
    // private Set<Role> roles;

    // ... existing code ...
}

