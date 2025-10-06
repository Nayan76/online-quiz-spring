package com.example.quiz.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String id;
    private String title;
    private List<Question> questions = new ArrayList<>();

    public Quiz() {}
    public Quiz(String id, String title) { this.id = id; this.title = title; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public void addQuestion(Question q) { this.questions.add(q); }
}
