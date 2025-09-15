package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.QuizAttemptDTO;
import com.iqbrave.iqbrave_lms.dto.StudentAnswerDTO;
import com.iqbrave.iqbrave_lms.entity.*;
import com.iqbrave.iqbrave_lms.repository.*;
import com.iqbrave.iqbrave_lms.service.QuizAttemptService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizAttemptServiceImpl implements QuizAttemptService {

    private final QuizAttemptRepository attemptRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final StudentAnswerRepository studentAnswerRepository;

    public QuizAttemptServiceImpl(QuizAttemptRepository attemptRepository,
                                  QuizRepository quizRepository,
                                  UserRepository userRepository,
                                  StudentAnswerRepository studentAnswerRepository) {
        this.attemptRepository = attemptRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.studentAnswerRepository = studentAnswerRepository;
    }

    @Override
    public QuizAttempt startAttempt(Long quizId, Long studentId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setStudent(student);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setScore(0);

        return attemptRepository.save(attempt);
    }

    @Override
    public QuizAttempt submitAttempt(Long attemptId, QuizAttemptDTO attemptDTO) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        List<StudentAnswer> answerEntities = new ArrayList<>();
        if (attemptDTO.getAnswers() != null) {
            for (StudentAnswerDTO dto : attemptDTO.getAnswers()) {
                StudentAnswer answer = new StudentAnswer();
                answer.setAttempt(attempt);
                answer.setAnswer(dto.getAnswer());
                answer.setCorrect(dto.isCorrect());
                answer.setMarks(dto.isCorrect() ? 1 : 0); // 1 mark per correct answer
                // You can also link the Question if needed
                answerEntities.add(answer);
            }
            studentAnswerRepository.saveAll(answerEntities);
            attempt.setAnswers(answerEntities);
        }

        int totalScore = answerEntities.stream().mapToInt(StudentAnswer::getMarks).sum();
        attempt.setScore(totalScore);
        attempt.setEndTime(LocalDateTime.now());

        return attemptRepository.save(attempt);
    }

    @Override
    public List<QuizAttempt> getAttemptsByStudent(Long studentId) {
        return attemptRepository.findByStudent_Id(studentId);
    }

    @Override
    public List<QuizAttempt> getAttemptsByQuiz(Long quizId) {
        return attemptRepository.findByQuiz_Id(quizId);
    }
}
