package com.iqbrave.iqbrave_lms.repository;

import com.iqbrave.iqbrave_lms.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<CourseModule, Long> {

    // ✅ Find all modules by the owning course's id (nested property traversal)
    List<CourseModule> findByCourse_Id(Long courseId);

    // ✅ Example: search modules by title (case-insensitive)
    List<CourseModule> findByTitleContainingIgnoreCase(String title);
    Optional<CourseModule> findByTitle(String title);
}
