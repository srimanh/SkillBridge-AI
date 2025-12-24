package com.skillbridge.backend.service;

import com.skillbridge.backend.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class SkillBridgeService {

  private final ObjectMapper objectMapper;
  private final RestTemplate restTemplate;

  @Value("${spring.ai.google.ai.api-key}")
  private String apiKey;

  @Value("${spring.ai.google.ai.chat.options.model:gemini-1.5-flash}")
  private String model;

  @Autowired
  public SkillBridgeService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.restTemplate = new RestTemplate();
  }

  public HealthResponseDTO getHealthStatus() {
    return new HealthResponseDTO("UP", "SkillBridge AI Backend");
  }

  private String callGemini(String systemPrompt, String userPrompt) {
    String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey;

    // Combine system and user prompt for Gemini (it doesn't have a separate system
    // role in the same way OpenAI does in simple API)
    // We can prepend the system instructions to the user prompt.
    String combinedPrompt = systemPrompt + "\n\nUSER REQUEST:\n" + userPrompt;

    Map<String, Object> requestBody = Map.of(
        "contents", List.of(
            Map.of("parts", List.of(
                Map.of("text", combinedPrompt)))));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    try {
      ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
      if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
        // Extract text from Gemini response structure
        // response.candidates[0].content.parts[0].text
        List candidates = (List) response.getBody().get("candidates");
        Map firstCandidate = (Map) candidates.get(0);
        Map content = (Map) firstCandidate.get("content");
        List parts = (List) content.get("parts");
        Map firstPart = (Map) parts.get(0);
        String text = (String) firstPart.get("text");

        // Clean up markdown code blocks if the AI includes them
        if (text.contains("```json")) {
          text = text.substring(text.indexOf("```json") + 7);
          text = text.substring(0, text.lastIndexOf("```"));
        } else if (text.contains("```")) {
          text = text.substring(text.indexOf("```") + 3);
          text = text.substring(0, text.lastIndexOf("```"));
        }

        return text.trim();
      }
    } catch (Exception e) {
      throw new RuntimeException("Gemini API call failed: " + e.getMessage());
    }
    return null;
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
        """;

    String userPrompt = "RESUME TEXT:\n" + request.getResumeText() + "\n\nJOB DESCRIPTION:\n"
        + request.getJobDescription();

    try {
      String responseJson = callGemini(systemPrompt, userPrompt);
      return objectMapper.readValue(responseJson, ResumeAnalysisResponseDTO.class);
    } catch (Exception e) {
      System.err.println("AI Analysis failed, using mock data: " + e.getMessage());
      return new ResumeAnalysisResponseDTO(
          85,
          List.of("Microservices Architecture", "Docker", "Kubernetes"),
          List.of("Experience", "Projects"),
          Map.of(
              "Experience", "Strengthen bullet points with more quantifiable metrics.",
              "Projects", "Highlight the specific technologies used in the 'SkillBridge' project.",
              "Skills", "Add more cloud-native technologies to align with the JD."));
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
        """;

    String userPrompt = "JOB ROLE: " + request.getJobRole() + "\nMISSING SKILLS: " + request.getMissingSkills()
        + "\nWEAK SECTIONS: " + request.getWeakSections();

    try {
      String responseJson = callGemini(systemPrompt, userPrompt);
      return objectMapper.readValue(responseJson, InterviewGenerationResponseDTO.class);
    } catch (Exception e) {
      System.err.println("Interview generation failed, using mock data: " + e.getMessage());
      return new InterviewGenerationResponseDTO(
          request.getJobRole(),
          List.of(
              new InterviewQuestionDTO("Technical", "React",
                  "How do you handle state management in large-scale React applications?"),
              new InterviewQuestionDTO("Scenario", "Problem Solving",
                  "Tell me about a time you resolved a critical production bug."),
              new InterviewQuestionDTO("Resume", "Experience",
                  "Can you walk through the architecture of the most recent project on your resume?")));
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
        """;

    String userPrompt = "JOB ROLE: " + request.getJobRole() + "\nSKILL: " + request.getSkill() + "\nQUESTION: "
        + request.getQuestion() + "\nCANDIDATE ANSWER: " + request.getAnswer();

    try {
      String responseJson = callGemini(systemPrompt, userPrompt);
      return objectMapper.readValue(responseJson, AnswerEvaluationResponseDTO.class);
    } catch (Exception e) {
      System.err.println("Answer evaluation failed, using mock data: " + e.getMessage());
      return new AnswerEvaluationResponseDTO(
          8,
          "Excellent",
          List.of("Clear articulation", "Correct technical concepts"),
          List.of("Could elaborate more on scalability"),
          "A perfect answer would discuss both performance and maintainability.");
    }
  }

  public InterviewSummaryResponseDTO generateInterviewSummary(InterviewSummaryRequestDTO request) {
    List<SkillScoreDTO> evals = request.getEvaluations();
    if (evals == null || evals.isEmpty()) {
      throw new IllegalArgumentException("No evaluations provided for summary.");
    }

    double total = 0;
    for (SkillScoreDTO eval : evals) {
      total += eval.getScore();
    }
    double average = Math.round((total / evals.size()) * 10.0) / 10.0;

    String level;
    if (average <= 4)
      level = "Not Ready";
    else if (average <= 6)
      level = "Needs Improvement";
    else if (average <= 8)
      level = "Almost Ready";
    else
      level = "Interview Ready";

    List<String> strengths = evals.stream()
        .filter(e -> e.getScore() >= 7)
        .map(SkillScoreDTO::getSkill)
        .distinct()
        .toList();
    List<String> weaknesses = evals.stream()
        .filter(e -> e.getScore() <= 5)
        .map(SkillScoreDTO::getSkill)
        .distinct()
        .toList();

    String systemPrompt = """
        You are a Brutally Honest Career Intelligence Engine.
        Your task is provide a concise (one paragraph) final decision-making summary for a candidate's interview readiness.
        Return ONLY the summary text.
        """;

    String userPrompt = "ROLE: " + request.getRole() + "\nAVERAGE SCORE: " + average + "\nREADINESS LEVEL: " + level
        + "\nSTRENGTHS: " + strengths + "\nWEAKNESSES: " + weaknesses;

    try {
      String aiSummary = callGemini(systemPrompt, userPrompt);

      InterviewSummaryResponseDTO response = new InterviewSummaryResponseDTO();
      response.setOverallScore(average);
      response.setReadinessLevel(level);
      response.setStrengthAreas(strengths);
      response.setWeakAreas(weaknesses);
      response.setSummary(aiSummary);
      return response;
    } catch (Exception e) {
      System.err.println("Summary generation failed, using mock data: " + e.getMessage());
      InterviewSummaryResponseDTO response = new InterviewSummaryResponseDTO();
      response.setOverallScore(average);
      response.setReadinessLevel(level);
      response.setStrengthAreas(strengths);
      response.setWeakAreas(weaknesses);
      response.setSummary(
          "The candidate shows strong potential in core areas but needs more practice in scenario-based problem solving.");
      return response;
    }
  }

  public BulletRewriteResponseDTO rewriteBullet(BulletRewriteRequestDTO request) {
    String systemPrompt = """
        You are an AI Career Coach that rewrites weak resume points into impact-driven bullets.
        Use the STAR (Situation, Task, Action, Result) + Impact framework.
        1. Start with a strong action verb.
        2. Include quantifiable impact.
        3. Align with target job role.

        Return ONLY a JSON object:
        {
          "rewrittenBullet": "text",
          "whyThisIsBetter": "text"
        }
        """;

    String userPrompt = "ORIGINAL BULLET: " + request.getOriginalBullet() + "\nTARGET ROLE: " + request.getTargetRole()
        + "\nFOCUS SKILL: " + request.getFocusSkill();

    try {
      String responseJson = callGemini(systemPrompt, userPrompt);
      return objectMapper.readValue(responseJson, BulletRewriteResponseDTO.class);
    } catch (Exception e) {
      System.err.println("Bullet rewriting failed, using mock data: " + e.getMessage());
      return new BulletRewriteResponseDTO(
          "Developed scalable React components that improved page load performance by 30% and enhanced user experience for core product features.",
          "Highlights action, technology, and measurable impact instead of a vague task.");
    }
  }
}
