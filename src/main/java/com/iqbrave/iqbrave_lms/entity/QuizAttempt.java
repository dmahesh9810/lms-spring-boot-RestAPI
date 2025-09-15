package com.iqbrave.iqbrave_lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "quiz_attempts")
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;  // When attempt starts
    private LocalDateTime endTime;    // When attempt ends
    private int score;                // Total score of attempt

    // Student reference
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Quiz reference
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAnswer> answers;
}
