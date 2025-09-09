package com.iqbrave.iqbrave_lms.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private String title;
    private String description;
    private Long instructor_id; // optional
}
