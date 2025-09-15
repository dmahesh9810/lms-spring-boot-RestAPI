package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.*;
import com.iqbrave.iqbrave_lms.entity.*;
import com.iqbrave.iqbrave_lms.repository.*;
import com.iqbrave.iqbrave_lms.util.QuizMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final StudentAnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public QuizServiceImpl(QuizRepository quizRepository,
                           QuestionRepository questionRepository,
                           QuizAttemptRepository attemptRepository,
                           StudentAnswerRepository answerRepository,
                           UserRepository userRepository,
                           CourseRepository courseRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    // ✅ Quiz CRUD
    @Override
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Course course = courseRepository.findById(quizDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        int computedDuration = (quizDTO.getDuration() == null || quizDTO.getDuration() <= 0) ? 10 : quizDTO.getDuration();

        Quiz quiz = Quiz.builder()
                .title(quizDTO.getTitle())
                .description(quizDTO.getDescription())
                .duration(computedDuration)
                .course(course)
                .build();

        Quiz saved = quizRepository.save(quiz);
        return QuizMapper.toQuizDTO(saved); // ✅ return DTO
    }

    @Override
    public QuizDTO updateQuiz(Long id, QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        // Only update duration if explicitly provided and valid; otherwise keep existing
        if (quizDTO.getDuration() != null && quizDTO.getDuration() > 0) {
            quiz.setDuration(quizDTO.getDuration());
        }
        return QuizMapper.toQuizDTO(quizRepository.save(quiz));
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDTO getQuiz(Long id) {
        return QuizMapper.toQuizDTO(quizRepository.findById(id).orElseThrow());
    }

    // Provide alias expected by tests
    public QuizDTO getQuizById(Long id) {
        // ... existing code ...
        return getQuiz(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizDTO> getQuizzesByCourse(Long courseId) {
        List<Quiz> quizzes = quizRepository.findByCourse_Id(courseId);
        return quizzes.stream().map(QuizMapper::toQuizDTO).collect(Collectors.toList());
    }

    // ✅ Questions
    @Override
    public QuestionDTO addQuestion(Long quizId, QuestionDTO dto) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        Question q = QuizMapper.toQuestionEntity(dto);
        q.setQuiz(quiz);
        return QuizMapper.toQuestionDTO(questionRepository.save(q));
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    // ✅ Attempts
    @Override
    public QuizAttemptDTO attemptQuiz(Long quizId, QuizAttemptRequestDTO request) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        QuizAttempt attempt = QuizAttempt.builder()
                .quiz(quiz)
                .student(student)
                .startTime(LocalDateTime.now())
                .score(0)
                .build();

        // Auto-grade
        int totalScore = 0;
        for (StudentAnswerDTO dto : request.getAnswers()) {
            Question q = questionRepository.findById(dto.getQuestionId()).orElseThrow();
            boolean correct = q.getCorrectAnswer().equalsIgnoreCase(dto.getAnswer());
            if (correct) totalScore += q.getMarks();

            StudentAnswer ans = StudentAnswer.builder()
                    .attempt(attempt)
                    .question(q)
                    .answer(dto.getAnswer())
                    .isCorrect(correct)
                    .build();
            answerRepository.save(ans);
        }
        attempt.setScore(totalScore);
        attempt.setEndTime(LocalDateTime.now());

        return QuizMapper.toQuizAttemptDTO(attemptRepository.save(attempt));
    }

    @Override
    public QuizAttemptDTO getAttemptResult(Long quizId, Long studentId) {
        QuizAttempt attempt = attemptRepository.findByQuiz_IdAndStudent_Id(quizId, studentId)
                .orElseThrow(() -> new RuntimeException("No attempt found"));
        return QuizMapper.toQuizAttemptDTO(attempt);
    }

    @Override
    public List<QuizAttemptDTO> listAttemptsForQuiz(Long quizId) {
        return attemptRepository.findByQuiz_Id(quizId)
                .stream().map(QuizMapper::toQuizAttemptDTO).collect(Collectors.toList());
    }
}
