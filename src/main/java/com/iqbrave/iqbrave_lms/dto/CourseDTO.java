package com.iqbrave.iqbrave_lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDTO {
    private Long id; // optional

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Instructor ID is required")
    @JsonProperty("instructor_id")
    private Long instructor_id;

    private boolean active = true;

    // Convenience constructor
    public CourseDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
