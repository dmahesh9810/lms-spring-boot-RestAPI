package com.iqbrave.iqbrave_lms.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String filePath;
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "module_id")
    @JsonBackReference
    private CourseModule courseModule;

    // Convenience constructor used in tests
    public Lesson(Long id, String title, String description, CourseModule courseModule) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.courseModule = courseModule;
    }
}

