package com.skillbridge.backend.service;

import com.skillbridge.backend.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

  public InterviewGenerationResponseDTO generateInterviewQuestions(InterviewGenerationRequestDTO request) {
    String systemPrompt = """
        You are an Expert Technical Interviewer.
        Your goal is to generate targeted, role-specific interview questions based on a candidate's missing skills and weak resume sections.

        Strictly follow these rules:
        1. Generate 5–7 high-quality interview questions.
        2. Mix question types: Technical (skills-based), Scenario-based, and Resume-specific probing.
        3. Align questions directly with the provided missing skills and weak sections.
        4. Keep questions professional, challenging, and realistic for the job role.
        5. Do NOT provide answers or evaluation.

        You MUST return ONLY a valid JSON object matching this schema:
        {
          "role": "Job Role",
          "questions": [
            {
              "type": "Technical | Scenario | Resume",
              "skill": "Specific Skill or Section",
              "question": "The question text"
            }
          ]
        }

        No markdown, no explanations outside JSON.
        """;

    String userPrompt = """
        JOB ROLE: {jobRole}
        MISSING SKILLS: {missingSkills}
        WEAK SECTIONS: {weakSections}
        """;

    PromptTemplate template = new PromptTemplate(userPrompt);
    Map<String, Object> model = Map.of(
        "jobRole", request.getJobRole(),
        "missingSkills", request.getMissingSkills() != null ? request.getMissingSkills() : List.of(),
        "weakSections", request.getWeakSections() != null ? request.getWeakSections() : List.of());

    try {
      String responseJson = chatClient.prompt()
          .system(systemPrompt)
          .user(template.create(model).getContents())
          .call()
          .content();

      return objectMapper.readValue(responseJson, InterviewGenerationResponseDTO.class);
    } catch (Exception e) {
      throw new RuntimeException("Interview Question generation failed: " + e.getMessage());
    }
  }

  public AnswerEvaluationResponseDTO evaluateAnswer(AnswerEvaluationRequestDTO request) {
    String systemPrompt = """
        You are a Senior Technical Interviewer and Expert Developer.
        Your task is to evaluate a candidate's answer to a technical interview question.

        Strictly follow these rules:
        1. Evaluate based on: Technical correctness, Depth of explanation, and Real-world relevance.
        2. Score the answer from 1 to 10.
        3. Be professional and brutally honest but constructive (senior engineer level).
        4. Provide a list of strengths and a list of specific improvements.
        5. Provide a sample 'Better Answer' that would score a 10/10.

        You MUST return ONLY a valid JSON object matching this schema:
        {
          "score": number,
          "verdict": "Terrible | Poor | Average | Good | Excellent",
          "strengths": ["strength1", "strength2"],
          "improvements": ["improvement1", "improvement2"],
          "sampleBetterAnswer": "text"
        }

        No markdown, no explanations outside JSON.
        """;

    String userPrompt = """
        JOB ROLE: {jobRole}
        SKILL: {skill}
        QUESTION: {question}
        CANDIDATE ANSWER: {answer}
        """;

    PromptTemplate template = new PromptTemplate(userPrompt);
    Map<String, Object> model = Map.of(
        "jobRole", request.getJobRole(),
        "skill", request.getSkill(),
        "question", request.getQuestion(),
        "answer", request.getAnswer());

    try {
      String responseJson = chatClient.prompt()
          .system(systemPrompt)
          .user(template.create(model).getContents())
          .call()
          .content();

      return objectMapper.readValue(responseJson, AnswerEvaluationResponseDTO.class);
    } catch (Exception e) {
      throw new RuntimeException("Answer evaluation failed: " + e.getMessage());
    }
  }
}
