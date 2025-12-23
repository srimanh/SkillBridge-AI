package com.skillbridge.backend.dto;

import java.util.List;

public class AnswerEvaluationResponseDTO {
    private int score;
    private String verdict;
    private List<String> strengths;
    private List<String> improvements;
    private String sampleBetterAnswer;

    public AnswerEvaluationResponseDTO() {
    }

    public AnswerEvaluationResponseDTO(int score, String verdict, List<String> strengths, List<String> improvements,
            String sampleBetterAnswer) {
        this.score = score;
        this.verdict = verdict;
        this.strengths = strengths;
        this.improvements = improvements;
        this.sampleBetterAnswer = sampleBetterAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getImprovements() {
        return improvements;
    }

    public void setImprovements(List<String> improvements) {
        this.improvements = improvements;
    }

    public String getSampleBetterAnswer() {
        return sampleBetterAnswer;
    }

    public void setSampleBetterAnswer(String sampleBetterAnswer) {
        this.sampleBetterAnswer = sampleBetterAnswer;
    }
}
