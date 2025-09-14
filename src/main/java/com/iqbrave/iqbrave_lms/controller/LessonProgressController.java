package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.LessonProgressDTO;
import com.iqbrave.iqbrave_lms.entity.Role;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import com.iqbrave.iqbrave_lms.service.LessonProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;
    private final UserRepository userRepository;

    /**
     * Mark a lesson as completed by the currently authenticated student.
     * No need to pass studentId in the body.
     */
    @PostMapping("/complete")
    public LessonProgressDTO markLessonCompleted(
            Principal principal,
            @RequestBody LessonProgressDTO dto) {

        // Get currently logged-in student from token
        User student = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Set studentId in DTO from an authenticated user
        dto.setStudentId(student.getId());

        return lessonProgressService.markLessonCompleted(dto);
    }

    /**
     * Get all progress for a student (any role can view)
     */
    @GetMapping("/student/{studentId}")
    public List<LessonProgressDTO> getProgress(@PathVariable Long studentId) {
        return lessonProgressService.getProgressByStudent(studentId);
    }

    /**
     * Get completion percentage for a student in a course
     */
    @GetMapping("/student/{studentId}/course/{courseId}/percentage")
    public double getCompletionPercentage(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            Principal principal) {

        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // If the current user is a STUDENT, they can only access their own percentage
        if (currentUser.getRole() == Role.STUDENT && !currentUser.getId().equals(studentId)) {
            throw new RuntimeException("Access denied: students can only view their own progress");
        }

        return lessonProgressService.getCompletionPercentage(studentId, courseId);
    }
}
