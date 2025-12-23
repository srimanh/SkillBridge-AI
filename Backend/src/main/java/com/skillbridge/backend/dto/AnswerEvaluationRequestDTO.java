package com.skillbridge.backend.dto;

public class AnswerEvaluationRequestDTO {
    private String question;
    private String answer;
    private String skill;
    private String jobRole;

    public AnswerEvaluationRequestDTO() {
    }

    public AnswerEvaluationRequestDTO(String question, String answer, String skill, String jobRole) {
        this.question = question;
        this.answer = answer;
        this.skill = skill;
        this.jobRole = jobRole;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }
}
