package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.QuestionDTO;
import com.iqbrave.iqbrave_lms.entity.Question;
import com.iqbrave.iqbrave_lms.entity.Quiz;
import com.iqbrave.iqbrave_lms.repository.QuestionRepository;
import com.iqbrave.iqbrave_lms.repository.QuizRepository;
import com.iqbrave.iqbrave_lms.util.QuizMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public QuestionDTO addQuestion(Long quizId, QuestionDTO questionDTO) {
        // Fetch the quiz entity
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Question question = QuizMapper.toQuestionEntity(questionDTO);
        question.setQuiz(quiz);

        return QuizMapper.toQuestionDTO(questionRepository.save(question));
    }

    @Override
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setQuestionText(questionDTO.getQuestionText());
        question.setType(questionDTO.getType());
        question.setOptions(questionDTO.getOptions());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setMarks(questionDTO.getMarks());

        return QuizMapper.toQuestionDTO(questionRepository.save(question));
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<QuestionDTO> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuizId(quizId)
                .stream()
                .map(QuizMapper::toQuestionDTO)
                .collect(Collectors.toList());
    }
}
