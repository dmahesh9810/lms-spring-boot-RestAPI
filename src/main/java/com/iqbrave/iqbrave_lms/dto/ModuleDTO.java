package com.iqbrave.iqbrave_lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@jakarta.validation.GroupSequence({ModuleDTO.class, ModuleDTO.NotBlankGroup.class, ModuleDTO.SizeGroup.class})
public class ModuleDTO {
    private Long id;

    // Define validation groups
    public interface NotBlankGroup {}
    public interface SizeGroup {}

    @NotBlank(message = "Title is required", groups = NotBlankGroup.class)
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters", groups = SizeGroup.class)
    private String title;

    @NotBlank(message = "Description is required", groups = NotBlankGroup.class)
    @Size(max = 500, message = "Description cannot exceed 500 characters", groups = SizeGroup.class)
    private String description;

    @NotNull(message = "Course ID is required")
    @JsonProperty("course_id")
    private Long courseId;

    private List<LessonDTO> lessons; // optional in request
}
