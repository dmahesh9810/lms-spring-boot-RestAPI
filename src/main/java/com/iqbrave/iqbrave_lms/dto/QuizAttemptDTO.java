package com.iqbrave.iqbrave_lms.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAttemptDTO {
    private Long id;
    private Long studentId;
    private Long quizId;
    private int score;
    private List<StudentAnswerDTO> answers;
}
