package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.LessonProgressDTO;
import com.iqbrave.iqbrave_lms.entity.*;
import com.iqbrave.iqbrave_lms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgressServiceImpl implements LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final UserRepository userRepository;
    private final ModuleRepository courseModuleRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public LessonProgressDTO markLessonCompleted(LessonProgressDTO dto) {
        User student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Lesson lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        LessonProgress progress = lessonProgressRepository
                .findByStudentIdAndLessonId(student.getId(), lesson.getId())
                .orElse(LessonProgress.builder()
                        .student(student)
                        .lesson(lesson)
                        .completed(false)
                        .build());

        progress.setCompleted(dto.isCompleted());
        lessonProgressRepository.save(progress);

        dto.setId(progress.getId());
        return dto;
    }

    @Override
    public List<LessonProgressDTO> getProgressByStudent(Long studentId) {
        userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return lessonProgressRepository.findByStudentId(studentId).stream()
                .map(p -> new LessonProgressDTO(p.getId(), studentId, p.getLesson().getId(), p.isCompleted()))
                .collect(Collectors.toList());
    }

    @Override
    public double getCompletionPercentage(Long studentId, Long courseId) {
        // Check student
        userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Get all lessons in the course
        List<Lesson> allLessons = course.getCourseModules().stream()
                .flatMap(m -> m.getLessons().stream())
                .toList();

        if (allLessons.isEmpty()) return 0;

        // Fetch all progress for this student
        List<LessonProgress> studentProgress = lessonProgressRepository.findByStudentId(studentId);

        // Count completed lessons
        long completedCount = allLessons.stream()
                .filter(lesson -> studentProgress.stream()
                        .anyMatch(p -> p.getLesson().getId().equals(lesson.getId()) && p.isCompleted()))
                .count();

        return (completedCount * 100.0) / allLessons.size();
    }

}
