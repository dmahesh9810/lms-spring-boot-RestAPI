package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.CourseModule;
import com.iqbrave.iqbrave_lms.repository.CourseRepository;
import com.iqbrave.iqbrave_lms.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ModuleService {


    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;


    public ModuleServiceImpl(ModuleRepository moduleRepository, CourseRepository courseRepository) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
    }


    @Override
    public CourseModule createModule(ModuleDTO dto) {
        CourseModule courseModule = new CourseModule();
        courseModule.setTitle(dto.getTitle());
        courseModule.setDescription(dto.getDescription());

        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException(
                            "Course not found with id " + dto.getCourseId()));
            courseModule.setCourse(course);
        }

        return moduleRepository.save(courseModule);
    }

    @Override
    public CourseModule updateModule(Long id, ModuleDTO dto) {
        CourseModule existing = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id " + id));


        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());


        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found with id " + dto.getCourseId()));
            existing.setCourse(course);
        }


        return moduleRepository.save(existing);
    }


    @Override
    @Transactional
    public void deleteModule(Long id) {
        CourseModule existing = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id " + id));
        moduleRepository.delete(existing);
    }


    @Override
    public List<CourseModule> listModules() {
        return moduleRepository.findAll();
    }


    @Override
    public Optional<CourseModule> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }
}