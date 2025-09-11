package com.iqbrave.iqbrave_lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDTO {
    private Long id;
    private String title;
    private String description;
    @JsonProperty("course_id")
    private Long courseId;
    private List<LessonDTO> lessons; // optional: for nested response
}

