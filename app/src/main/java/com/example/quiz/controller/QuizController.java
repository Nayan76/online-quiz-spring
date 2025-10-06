package com.example.quiz.controller;


import com.example.quiz.dto.AddQuestionRequest;
import com.example.quiz.dto.CreateQuizRequest;
import com.example.quiz.dto.ScoreResponse;
import com.example.quiz.dto.SubmitRequest;
import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody CreateQuizRequest req){
        Quiz q = service.createQuiz(req.getTitle());
        return ResponseEntity.created(URI.create("/api/quizzes/"+q.getId())).body(q);
    }

    @GetMapping
    public List<Map<String,String>> listQuizzes(){
        return service.listQuizzes();
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<?> addQuestion(@PathVariable String quizId, @Valid @RequestBody AddQuestionRequest req){
        try {
            Question q = service.addQuestion(quizId, req);
            return ResponseEntity.status(201).body(q);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<?> getQuestions(@PathVariable String quizId) {
        try {
            return ResponseEntity.ok(service.getQuestionsForQuiz(quizId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<?> submitQuiz(@PathVariable String quizId, @Valid @RequestBody SubmitRequest req){
        try{
            ScoreResponse res = service.scoreSubmission(quizId, req);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
}
