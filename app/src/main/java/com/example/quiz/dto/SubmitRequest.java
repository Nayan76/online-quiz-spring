package com.example.quiz.dto;

import java.util.List;

public class SubmitRequest {
    public static class AnswerDTO {
        private String questionId;
        // NOTE: field name must match the JSON you send: "selectedOptionIds"
        private List<String> selectedOptionIds;
        private String textAnswer;

        public String getQuestionId() { return questionId; }
        public void setQuestionId(String questionId) { this.questionId = questionId; }

        public List<String> getSelectedOptionIds() { return selectedOptionIds; }
        public void setSelectedOptionIds(List<String> selectedOptionIds) { this.selectedOptionIds = selectedOptionIds; }

        public String getTextAnswer() { return textAnswer; }
        public void setTextAnswer(String textAnswer) { this.textAnswer = textAnswer; }
    }

    private List<AnswerDTO> answers;
    public List<AnswerDTO> getAnswers() { return answers; }
    public void setAnswers(List<AnswerDTO> answers) { this.answers = answers; }
}

