package com.example.quiz.service;


import com.example.quiz.dto.AddQuestionRequest;
import com.example.quiz.dto.ScoreResponse;
import com.example.quiz.dto.SubmitRequest;

import com.example.quiz.model.Option;
import com.example.quiz.model.Quiz;
import com.example.quiz.model.Question;

import com.example.quiz.repo.InMemoryQuizRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final InMemoryQuizRepository repo;

    public QuizService(InMemoryQuizRepository repo) {
        this.repo = repo;
    }

    public Quiz createQuiz(String title) {
        Quiz q = new  Quiz(UUID.randomUUID().toString(), title);
        repo.save(q);
        return q;
    }

    public List<Map<String, String>> listQuizzes() {
        return repo.findAll().stream()
                .map(q -> Map.of("id", q.getId(), "title", q.getTitle()))
                .collect(Collectors.toList());
    }

    public Question addQuestion(String quizId, AddQuestionRequest req) {
        Quiz quiz = repo.findById(quizId);
        if (quiz == null) throw new NoSuchElementException("Quiz not found");

        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setText(req.getText());
        question.setType(req.getType());

        if ("text".equalsIgnoreCase(req.getType())) {
            if (req.getCorrectAnswer() == null) throw new IllegalArgumentException("text answer required");
            if (req.getCorrectAnswer().length()>300) throw new IllegalArgumentException("text answer too long (max 300)");
            question.setCorrectAnswer(req.getCorrectAnswer().trim());
        } else {
            if (req.getOptions() == null || req.getOptions().isEmpty())
                throw new IllegalArgumentException("options required for choice questions");
            question.setOptions(req.getOptions());
            question.setCorrectOptionIds(req.getCorrectOptionIds());
            if ("single".equalsIgnoreCase(req.getType())) {
                if (question.getCorrectOptionIds() == null || question.getCorrectOptionIds().size() != 1){
                    throw new IllegalArgumentException("single choice must have exactly one correct option");
                }
            }
        }

        quiz.addQuestion(question);
        repo.save(quiz);
        return question;
    }

    public List<Map<String, Object>> getQuestionsForQuiz(String quizId) {
        Quiz quiz = repo.findById(quizId);
        if (quiz == null) throw new NoSuchElementException("Quiz not found");
        List<Map<String, Object>> out = new ArrayList<>();
        for (Question q : quiz.getQuestions()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", q.getId());
            map.put("text", q.getText());
            map.put("type", q.getType());
            if (q.getOptions() != null) map.put("options", q.getOptions());
            out.add(map);
        }
        return out;
    }

    public ScoreResponse scoreSubmission(String quizId, SubmitRequest req) {
        Quiz quiz = repo.findById(quizId);
        if (quiz == null) throw new NoSuchElementException("Quiz not found");

        Map<String, Question> qmap = quiz.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, x -> x));

        int total = quiz.getQuestions().size();
        int score = 0;

        if (req == null || req.getAnswers()== null) throw new IllegalArgumentException("answers required");

        for (SubmitRequest.AnswerDTO a : req.getAnswers()) {
            if (a == null) continue;
            Question q = qmap.get(a.getQuestionId());
            if (q == null) continue;

            if ("text".equalsIgnoreCase(q.getType())) {
                String given = a.getTextAnswer() == null ? "" : a.getTextAnswer().trim();
                if (!given.isEmpty() && q.getCorrectAnswer() != null && given.equalsIgnoreCase(q.getCorrectAnswer())) {
                    score++;
                }
            } else {
                List<String> sel = a.getSelectedOptionIds() == null ? Collections.emptyList() : a.getSelectedOptionIds();
                List<String> correct = q.getCorrectOptionIds() == null ? Collections.emptyList() : q.getCorrectOptionIds();

                if ("single".equalsIgnoreCase(q.getType())) {
                    if (sel.size() == 1 && correct.size() == 1 && sel.get(0).equals(correct.get(0))) score++;
                } else {
                    Set<String> sSel = new HashSet<>(sel);
                    Set<String> sCorr = new HashSet<>(correct);
                    if (sSel.equals(sCorr)) score++;
                }
            }
        }
        return new ScoreResponse(score, total);
    }
}
