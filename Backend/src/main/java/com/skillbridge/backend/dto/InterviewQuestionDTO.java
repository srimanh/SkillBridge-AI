package com.skillbridge.backend.dto;

public class InterviewQuestionDTO {
    private String type;
    private String skill;
    private String question;

    public InterviewQuestionDTO() {
    }

    public InterviewQuestionDTO(String type, String skill, String question) {
        this.type = type;
        this.skill = skill;
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
