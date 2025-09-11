package com.iqbrave.iqbrave_lms.repository;

import com.iqbrave.iqbrave_lms.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    // ✅ Find all modules by the owning course's id (nested property traversal)
    List<Module> findByCourse_Id(Long courseId);

    // ✅ Example: search modules by title (case-insensitive)
    List<Module> findByTitleContainingIgnoreCase(String title);
}
