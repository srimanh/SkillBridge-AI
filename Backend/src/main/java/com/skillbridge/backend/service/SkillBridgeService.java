package com.skillbridge.backend.service;

import com.skillbridge.backend.dto.HealthResponseDTO;
import com.skillbridge.backend.dto.ResumeAnalysisRequestDTO;
import com.skillbridge.backend.dto.ResumeAnalysisResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class SkillBridgeService {

    public HealthResponseDTO getHealthStatus() {
        return new HealthResponseDTO("UP", "SkillBridge AI Backend");
    }

    public ResumeAnalysisResponseDTO analyzeResume(ResumeAnalysisRequestDTO request) {
        Map<String, String> feedback = new HashMap<>();
        feedback.put("Experience", "Lacks measurable impact");
        feedback.put("Projects", "Backend complexity not demonstrated");

        return new ResumeAnalysisResponseDTO(
                65,
                Arrays.asList("System Design", "REST APIs"),
                Arrays.asList("Projects"),
                feedback);
    }
}
