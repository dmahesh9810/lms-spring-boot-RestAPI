package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.LessonDTO;
import com.iqbrave.iqbrave_lms.entity.Lesson;


import java.util.List;
import java.util.Optional;


public interface LessonService {


    Lesson createLesson(LessonDTO lessonDTO);


    Lesson updateLesson(Long id, LessonDTO lessonDTO);


    void deleteLesson(Long id);


    List<Lesson> listLessons();


    Optional<Lesson> getLessonById(Long id);
}