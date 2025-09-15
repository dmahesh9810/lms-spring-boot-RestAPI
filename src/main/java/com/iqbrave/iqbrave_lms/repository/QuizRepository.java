package com.iqbrave.iqbrave_lms.repository;

import com.iqbrave.iqbrave_lms.entity.Quiz;
import com.iqbrave.iqbrave_lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @EntityGraph(attributePaths = {"questions", "course"})
    List<Quiz> findByCourse_Id(Long courseId);
    List<Quiz> findByCourse(Course course);
}
