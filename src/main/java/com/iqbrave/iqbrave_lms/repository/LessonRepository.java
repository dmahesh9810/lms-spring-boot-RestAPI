package com.iqbrave.iqbrave_lms.repository;

import com.iqbrave.iqbrave_lms.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    // ✅ Find all lessons by module id
    List<Lesson> findByModuleId(Long moduleId);

    // ✅ Search lessons by title (case-insensitive)
    List<Lesson> findByTitleContainingIgnoreCase(String title);
}
