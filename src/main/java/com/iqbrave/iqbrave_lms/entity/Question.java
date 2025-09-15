package com.iqbrave.iqbrave_lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @Enumerated(EnumType.STRING)
    private QuestionType type; // MCQ, TRUE_FALSE, SHORT

    @ElementCollection
    private List<String> options; // For MCQ/TrueFalse

    private String correctAnswer;
    private int marks;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
