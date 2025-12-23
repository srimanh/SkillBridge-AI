package com.skillbridge.backend.dto;

import java.util.List;

public class InterviewSummaryResponseDTO {
    private double overallScore;
    private String readinessLevel;
    private List<String> strengthAreas;
    private List<String> weakAreas;
    private String summary;

    public InterviewSummaryResponseDTO() {
    }

    public double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
    }

    public String getReadinessLevel() {
        return readinessLevel;
    }

    public void setReadinessLevel(String readinessLevel) {
        this.readinessLevel = readinessLevel;
    }

    public List<String> getStrengthAreas() {
        return strengthAreas;
    }

    public void setStrengthAreas(List<String> strengthAreas) {
        this.strengthAreas = strengthAreas;
    }

    public List<String> getWeakAreas() {
        return weakAreas;
    }

    public void setWeakAreas(List<String> weakAreas) {
        this.weakAreas = weakAreas;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
