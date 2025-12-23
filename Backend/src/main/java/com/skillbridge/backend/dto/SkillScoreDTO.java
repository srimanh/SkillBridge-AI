package com.skillbridge.backend.dto;

public class SkillScoreDTO {
    private String skill;
    private int score;

    public SkillScoreDTO() {
    }

    public SkillScoreDTO(String skill, int score) {
        this.skill = skill;
        this.score = score;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
