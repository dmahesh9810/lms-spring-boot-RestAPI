package com.iqbrave.iqbrave_lms.repository;

import com.iqbrave.iqbrave_lms.entity.Lesson;
import com.iqbrave.iqbrave_lms.entity.LessonProgress;
import com.iqbrave.iqbrave_lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    // Find progress by student and lesson using IDs (better than entity)
    Optional<LessonProgress> findByStudentIdAndLessonId(Long studentId, Long lessonId);

    List<LessonProgress> findByStudentId(Long studentId);

    List<LessonProgress> findByLessonId(Long lessonId);
}
