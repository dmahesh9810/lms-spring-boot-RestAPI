package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.Module;
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
    public Module createModule(ModuleDTO dto) {
        Module module = new Module();
        module.setTitle(dto.getTitle());
        module.setDescription(dto.getDescription());

        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException(
                            "Course not found with id " + dto.getCourseId()));
            module.setCourse(course);
        }

        return moduleRepository.save(module);
    }

    @Override
    public Module updateModule(Long id, ModuleDTO dto) {
        Module existing = moduleRepository.findById(id)
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
        Module existing = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id " + id));
        moduleRepository.delete(existing);
    }


    @Override
    public List<Module> listModules() {
        return moduleRepository.findAll();
    }


    @Override
    public Optional<Module> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }
}