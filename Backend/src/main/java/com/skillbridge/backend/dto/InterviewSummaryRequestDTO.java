package com.skillbridge.backend.dto;

import java.util.List;

public class InterviewSummaryRequestDTO {
    private String role;
    private List<SkillScoreDTO> evaluations;

    public InterviewSummaryRequestDTO() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<SkillScoreDTO> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<SkillScoreDTO> evaluations) {
        this.evaluations = evaluations;
    }
}
