package com.iqbrave.iqbrave_lms.repository;

import com.iqbrave.iqbrave_lms.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByStudent_Id(Long studentId);

    List<QuizAttempt> findByQuiz_Id(Long quizId);

    // Add this method to find by both quiz and student
    Optional<QuizAttempt> findByQuiz_IdAndStudent_Id(Long quizId, Long studentId);
}
