package com.iqbrave.iqbrave_lms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonProgressDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Lesson ID is required")
    private Long lessonId;

    private boolean completed;
}
