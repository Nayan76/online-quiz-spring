package com.example.quiz.model;

import java.util.List;

public class Question {
    private String id;
    private String text;
    private String type; // "single", "multiple", "text"
    private List<Option> options;
    private List<String> correctOptionIds;
    private String correctAnswer;

    public Question() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }

    public List<String> getCorrectOptionIds() { return correctOptionIds; }
    public void setCorrectOptionIds(List<String> correctOptionIds) { this.correctOptionIds = correctOptionIds; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}
