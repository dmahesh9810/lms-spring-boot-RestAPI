package com.iqbrave.iqbrave_lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int duration; // in minutes

    // Link to Course
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Questions inside quiz
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @PrePersist
    private void setDefaultDurationIfMissing() {
        // If duration isn't provided (Jackson -> 0 for primitive int), set a default
        if (this.duration <= 0) {
            this.duration = 10; // default duration in minutes
        }
    }
}
