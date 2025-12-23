package com.skillbridge.backend.dto;

import java.util.List;

public class InterviewGenerationRequestDTO {
    private List<String> missingSkills;
    private List<String> weakSections;
    private String jobRole;

    public InterviewGenerationRequestDTO() {
    }

    public InterviewGenerationRequestDTO(List<String> missingSkills, List<String> weakSections, String jobRole) {
        this.missingSkills = missingSkills;
        this.weakSections = weakSections;
        this.jobRole = jobRole;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getWeakSections() {
        return weakSections;
    }

    public void setWeakSections(List<String> weakSections) {
        this.weakSections = weakSections;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }
}
