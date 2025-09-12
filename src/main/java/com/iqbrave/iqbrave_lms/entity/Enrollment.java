package com.iqbrave.iqbrave_lms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many students can enroll in many courses
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // ✅ Make sure these getters exist
    public Long getId() { return id; }
    public User getStudent() { return student; }
    public Course getCourse() { return course; }

    public void setId(Long id) { this.id = id; }
    public void setStudent(User student) { this.student = student; }
    public void setCourse(Course course) { this.course = course; }
}
