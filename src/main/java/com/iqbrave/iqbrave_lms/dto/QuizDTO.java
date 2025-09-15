package com.iqbrave.iqbrave_lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    // Duration is optional; if null or <= 0, service will apply a default
    private Integer duration;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private List<QuestionDTO> questions;
}
