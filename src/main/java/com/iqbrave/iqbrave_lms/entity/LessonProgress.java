package com.iqbrave.iqbrave_lms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lesson_progress")
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Student reference
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Lesson reference
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    // Completion status
    @Column(nullable = false)
    private boolean completed = false;
}
