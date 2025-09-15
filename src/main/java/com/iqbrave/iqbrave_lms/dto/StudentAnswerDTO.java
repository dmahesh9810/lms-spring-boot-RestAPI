package com.iqbrave.iqbrave_lms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswerDTO {
    private Long questionId;
    private String answer;
    private boolean isCorrect;
}
