package com.iqbrave.iqbrave_lms.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class CourseDTO {
    private Long id; // optional: used for responses/updates
    private String title;
    private String description;
    @JsonProperty("instructor_id")  // maps JSON "instructor_id" to this field
    private Long instructor_id; // keep snake_case
    private boolean active = true;
}