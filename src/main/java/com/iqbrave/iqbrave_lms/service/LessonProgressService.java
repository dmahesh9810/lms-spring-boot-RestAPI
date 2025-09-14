package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.LessonProgressDTO;

import java.util.List;

public interface LessonProgressService {

    LessonProgressDTO markLessonCompleted(LessonProgressDTO lessonProgressDTO);

    List<LessonProgressDTO> getProgressByStudent(Long studentId);

    double getCompletionPercentage(Long studentId, Long courseId);
}
