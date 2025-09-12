package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.LessonDTO;
import com.iqbrave.iqbrave_lms.entity.Lesson;
import com.iqbrave.iqbrave_lms.entity.CourseModule;
import com.iqbrave.iqbrave_lms.repository.LessonRepository;
import com.iqbrave.iqbrave_lms.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {


    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;


    public LessonServiceImpl(LessonRepository lessonRepository, ModuleRepository moduleRepository) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }


    @Override
    public Lesson createLesson(LessonDTO dto) {
        Lesson lesson = new Lesson();
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setFilePath(dto.getFilePath());
        lesson.setVideoUrl(dto.getVideoUrl());


        if (dto.getModuleId() != null) {
            CourseModule courseModule = moduleRepository.findById(dto.getModuleId())
                    .orElseThrow(() -> new RuntimeException("Module not found with id " + dto.getModuleId()));
            lesson.setCourseModule(courseModule);
        }


        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson updateLesson(Long id, LessonDTO dto) {
        Lesson existing = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with id " + id));


        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getFilePath() != null) existing.setFilePath(dto.getFilePath());
        if (dto.getVideoUrl() != null) existing.setVideoUrl(dto.getVideoUrl());


        if (dto.getModuleId() != null) {
            CourseModule courseModule = moduleRepository.findById(dto.getModuleId())
                    .orElseThrow(() -> new RuntimeException("Module not found with id " + dto.getModuleId()));
            existing.setCourseModule(courseModule);
        }


        return lessonRepository.save(existing);
    }


    @Override
    @Transactional
    public void deleteLesson(Long id) {
        Lesson existing = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with id " + id));
        lessonRepository.delete(existing);
    }


    @Override
    public List<Lesson> listLessons() {
        return lessonRepository.findAll();
    }
    @Override
    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }
}