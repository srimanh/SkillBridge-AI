package com.skillbridge.backend.service;

import com.skillbridge.backend.dto.HealthResponseDTO;
import com.skillbridge.backend.dto.ResumeAnalysisRequestDTO;
import com.skillbridge.backend.dto.ResumeAnalysisResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SkillBridgeService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public SkillBridgeService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public HealthResponseDTO getHealthStatus() {
        return new HealthResponseDTO("UP", "SkillBridge AI Backend");
    }

    public ResumeAnalysisResponseDTO analyzeResume(ResumeAnalysisRequestDTO request) {
        String systemPrompt = """
                You are a Senior Technical Recruiter and Career Coach.
                Your task is to analyze a resume against a specific job description.

                Strictly follow these rules:
                1. Act as a professional technical recruiter.
                2. Compare meaning, not just keywords (semantic analysis).
                3. Be strict and unbiased.
                4. Identify match percentage (0-100).
                5. List specific missing skills found in the JD but not in the resume.
                6. Identify weak sections in the resume (e.g., Experience, Projects, Skills).
                7. Provide actionable feedback for each section.

                You MUST return ONLY a valid JSON object matching this schema:
                {
                  "matchScore": number,
                  "missingSkills": ["skill1", "skill2"],
                  "weakSections": ["section1", "section2"],
                  "sectionFeedback": {
                    "Experience": "feedback text",
                    "Projects": "feedback text",
                    "Skills": "feedback text"
                  }
                }

                No markdown, no explanations outside JSON, no extra fields.
                """;

        String userPrompt = """
                RESUME TEXT:
                {resumeText}

                JOB DESCRIPTION:
                {jobDescription}
                """;

        PromptTemplate template = new PromptTemplate(userPrompt);
        Map<String, Object> model = Map.of(
                "resumeText", request.getResumeText(),
                "jobDescription", request.getJobDescription());

        try {
            String responseJson = chatClient.prompt()
                    .system(systemPrompt)
                    .user(template.create(model).getContents())
                    .call()
                    .content();

            return objectMapper.readValue(responseJson, ResumeAnalysisResponseDTO.class);
        } catch (Exception e) {
            // Basic error handling for invalid JSON or AI failure
            throw new RuntimeException("AI Analysis failed: " + e.getMessage());
        }
    }
}
