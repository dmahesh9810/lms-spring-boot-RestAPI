package com.iqbrave.iqbrave_lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;
    private String title;
    private String description;
    private String filePath;
    private String videoUrl;
    private Long moduleId; // reference only, not full Module
}

