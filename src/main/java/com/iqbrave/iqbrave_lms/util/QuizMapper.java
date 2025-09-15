package com.iqbrave.iqbrave_lms.util;

import com.iqbrave.iqbrave_lms.dto.*;
import com.iqbrave.iqbrave_lms.entity.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class QuizMapper {

    private QuizMapper() {}

    public static QuizDTO toQuizDTO(Quiz quiz) {
        if (quiz == null) return null;
        List<QuestionDTO> qDtos = quiz.getQuestions() == null ? Collections.emptyList()
                : quiz.getQuestions().stream().map(QuizMapper::toQuestionDTO).collect(Collectors.toList());

        return QuizDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .duration(quiz.getDuration())
                .courseId(quiz.getCourse() != null ? quiz.getCourse().getId() : null)
                .questions(qDtos)
                .build();
    }

    public static QuestionDTO toQuestionDTO(Question q) {
        if (q == null) return null;
        return QuestionDTO.builder()
                .id(q.getId())
                .questionText(q.getQuestionText())
                .type(q.getType())
                .options(q.getOptions())
                .correctAnswer(q.getCorrectAnswer())
                .marks(q.getMarks())
                .build();
    }

    public static Question toQuestionEntity(QuestionDTO dto) {
        if (dto == null) return null;
        Question q = new Question();
        q.setQuestionText(dto.getQuestionText());
        q.setType(dto.getType());
        q.setOptions(dto.getOptions());
        q.setCorrectAnswer(dto.getCorrectAnswer());
        q.setMarks(dto.getMarks());
        return q;
    }

    public static QuizAttemptDTO toQuizAttemptDTO(QuizAttempt attempt) {
        if (attempt == null) return null;
        List<StudentAnswerDTO> answers = attempt.getAnswers() == null ? Collections.emptyList()
                : attempt.getAnswers().stream().map(a -> new StudentAnswerDTO(
                a.getQuestion() != null ? a.getQuestion().getId() : null,
                a.getAnswer(),
                a.isCorrect()
        )).collect(Collectors.toList());

        return QuizAttemptDTO.builder()
                .id(attempt.getId())
                .studentId(attempt.getStudent() != null ? attempt.getStudent().getId() : null)
                .quizId(attempt.getQuiz() != null ? attempt.getQuiz().getId() : null)
                .score(attempt.getScore())
                .answers(answers)
                .build();
    }
}
