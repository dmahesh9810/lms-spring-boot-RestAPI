package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.LessonDTO;
import com.iqbrave.iqbrave_lms.entity.Lesson;
import com.iqbrave.iqbrave_lms.service.LessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // Create a lesson
    @PostMapping
    public Lesson createLesson(@RequestBody @org.springframework.validation.annotation.Validated(LessonDTO.Create.class) LessonDTO lessonDTO) {
        return lessonService.createLesson(lessonDTO);
    }

    // Update lesson
    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable Long id, @RequestBody @org.springframework.validation.annotation.Validated(LessonDTO.Update.class) LessonDTO lessonDTO) {
        return lessonService.updateLesson(id, lessonDTO);
    }

    // Delete a lesson
    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return "Lesson deleted successfully";
    }

    // List all lessons
    @GetMapping
    public List<Lesson> listLessons() {
        return lessonService.listLessons();
    }

    // Get a single lesson
    @GetMapping("/{id}")
    public Lesson getLesson(@PathVariable Long id) {
        return lessonService.getLessonById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with id " + id));
    }

    // Optional: list lessons by module
    @GetMapping("/module/{moduleId}")
    public List<Lesson> getLessonsByModule(@PathVariable Long moduleId) {
        return lessonService.listLessons()
                .stream()
                .filter(l -> l.getCourseModule() != null && l.getCourseModule().getId().equals(moduleId))
                .toList();
    }
}
