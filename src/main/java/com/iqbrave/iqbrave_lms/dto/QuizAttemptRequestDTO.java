package com.iqbrave.iqbrave_lms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAttemptRequestDTO {
    @NotNull
    private Long studentId; // replace with principal extraction if desired

    @NotNull
    private List<StudentAnswerDTO> answers;
}
