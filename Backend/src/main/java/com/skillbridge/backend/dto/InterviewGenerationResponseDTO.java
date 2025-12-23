package com.skillbridge.backend.dto;

import java.util.List;

public class InterviewGenerationResponseDTO {
    private String role;
    private List<InterviewQuestionDTO> questions;

    public InterviewGenerationResponseDTO() {
    }

    public InterviewGenerationResponseDTO(String role, List<InterviewQuestionDTO> questions) {
        this.role = role;
        this.questions = questions;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<InterviewQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<InterviewQuestionDTO> questions) {
        this.questions = questions;
    }
}
