package com.example.quiz.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateQuizRequest {
    @NotBlank
    private String title;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
