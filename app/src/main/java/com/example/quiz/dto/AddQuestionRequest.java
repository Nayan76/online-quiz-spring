package com.example.quiz.dto;

import com.example.quiz.model.Option;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class AddQuestionRequest {
    @NotBlank private String text;
    @NotBlank private String type; // single|multiple|text
    private List<Option> options;
    private List<String> correctOptionsIds;
    private String CorrectAnswer;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }

    public List<String> getCorrectOptionIds() { return correctOptionsIds; }
    public void setCorrectOptionIds(List<String> correctOptionsIds) { this.correctOptionsIds = correctOptionsIds; }

    public String getCorrectAnswer() { return CorrectAnswer; }
    public void setCorrectAnswer(String CorrectAnswer) { this.CorrectAnswer = CorrectAnswer; }
}
