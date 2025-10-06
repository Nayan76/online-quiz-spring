package com.example.quiz.service;

import com.example.quiz.dto.AddQuestionRequest;
import com.example.quiz.dto.ScoreResponse;
import com.example.quiz.dto.SubmitRequest;
import com.example.quiz.model.Option;
import com.example.quiz.repo.InMemoryQuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizServiceTest {
    private QuizService service;

    @BeforeEach
    void setup() {
        service = new QuizService(new InMemoryQuizRepository());
    }

    @Test
    void testSingleChoiceScoring() {
        var quiz = service.createQuiz("t");
        var quizId = quiz.getId();

        var req = new AddQuestionRequest();
        req.setText("q1");
        req.setType("single");
        req.setOptions(List.of(new Option("a","A"), new Option("b","B")));
        req.setCorrectOptionIds(List.of("b"));
        var q = service.addQuestion(quizId, req);

        var submit = new SubmitRequest();
        var ans = new SubmitRequest.AnswerDTO();
        ans.setQuestionId(q.getId());
        ans.setSelectedOptionIds(List.of("b"));
        submit.setAnswers(List.of(ans));

        ScoreResponse res = service.scoreSubmission(quizId, submit);
        assertEquals(1, res.getScore());
        assertEquals(1, res.getTotal());
    }

    @Test
    void testTextScoring() {
        var quiz = service.createQuiz("t2");
        var quizId = quiz.getId();

        var req = new AddQuestionRequest();
        req.setText("What is 2+2?");
        req.setType("text");
        req.setCorrectAnswer("4");
        var q = service.addQuestion(quizId, req);

        var submit = new SubmitRequest();
        var ans = new SubmitRequest.AnswerDTO();
        ans.setQuestionId(q.getId());
        ans.setTextAnswer("4");
        submit.setAnswers(List.of(ans));

        ScoreResponse res = service.scoreSubmission(quizId, submit);
        assertEquals(1, res.getScore());
        assertEquals(1, res.getTotal());
    }
}

