package com.iqbrave.iqbrave_lms.dto;

import com.iqbrave.iqbrave_lms.entity.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;

    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotNull(message = "Question type is required")
    private QuestionType type;

    private List<String> options;
    private String correctAnswer;
    private int marks;

    // Convenience overloads to tolerate String/long inputs in tests or callers
    public void setType(String type) {
        // ... existing code ...
        this.type = type == null ? null : QuestionType.valueOf(type);
    }

    public void setMarks(long marks) {
        // ... existing code ...
        this.marks = (int) marks;
    }
}
