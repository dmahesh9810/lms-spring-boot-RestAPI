package com.iqbrave.iqbrave_lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;

    // Validation groups
    public interface Update {}

    // Subgroups for Create to control order: NotBlank first, then Size
    public interface CreateNotBlank {}
    public interface CreateSize {}

    @jakarta.validation.GroupSequence({CreateNotBlank.class, CreateSize.class})
    public interface Create {}

    @NotBlank(message = "Title is required", groups = {CreateNotBlank.class, Update.class})
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters", groups = {CreateSize.class, Update.class})
    private String title;

    @NotBlank(message = "Content is required", groups = {CreateNotBlank.class, Update.class})
    @Size(max = 1000, message = "Description cannot exceed 1000 characters", groups = {CreateSize.class, Update.class})
    private String content;

    @NotBlank(message = "File path is required", groups = {CreateNotBlank.class})
    private String filePath;

    @NotBlank(message = "Video URL is required", groups = {CreateNotBlank.class})
    private String videoUrl;

    @NotNull(message = "Module ID is required", groups = {CreateNotBlank.class})
    private Long moduleId;

    // Alias for description (maintain compatibility if any code uses description)
    public String getDescription() {
        return this.content;
    }
    public void setDescription(String description) {
        this.content = description;
    }

    // alias for content (kept for clarity)
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
