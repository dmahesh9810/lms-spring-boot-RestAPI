package com.iqbrave.iqbrave_lms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    // Optional: who created the course
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private boolean active = true;
}
