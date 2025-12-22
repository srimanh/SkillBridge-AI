package com.skillbridge.backend.dto;

import java.util.List;
import java.util.Map;

public class ResumeAnalysisResponseDTO {
    private int matchScore;
    private List<String> missingSkills;
    private List<String> weakSections;
    private Map<String, String> sectionFeedback;

    public ResumeAnalysisResponseDTO() {
    }

    public ResumeAnalysisResponseDTO(int matchScore, List<String> missingSkills, List<String> weakSections,
            Map<String, String> sectionFeedback) {
        this.matchScore = matchScore;
        this.missingSkills = missingSkills;
        this.weakSections = weakSections;
        this.sectionFeedback = sectionFeedback;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public List<String> missingSkills() {
        return missingSkills;
    }

    // Note: To match EXACT JSON keys if they are different from camelCase,
    // but here matchScore, missingSkills, weakSections, sectionFeedback are already
    // what's requested.
    // However, the user request shows "matchScore", "missingSkills", etc. which are
    // standard camelCase.

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

    public Map<String, String> getSectionFeedback() {
        return sectionFeedback;
    }

    public void setSectionFeedback(Map<String, String> sectionFeedback) {
        this.sectionFeedback = sectionFeedback;
    }
}
