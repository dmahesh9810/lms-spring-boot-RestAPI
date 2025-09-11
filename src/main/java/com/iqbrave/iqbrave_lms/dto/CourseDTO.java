package com.iqbrave.iqbrave_lms.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDTO {
    private Long id; // optional: used for responses/updates
    private String title;
    private String description;
    @JsonProperty("instructor_id")  // maps JSON "instructor_id" to this field
    private Long instructor_id; // keep snake_case
    private boolean active = true;

    // Convenience constructor used in tests
    public CourseDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }
}