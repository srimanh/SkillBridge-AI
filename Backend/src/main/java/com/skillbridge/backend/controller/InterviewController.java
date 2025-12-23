package com.skillbridge.backend.controller;

import com.skillbridge.backend.dto.InterviewGenerationRequestDTO;
import com.skillbridge.backend.dto.InterviewGenerationResponseDTO;
import com.skillbridge.backend.service.SkillBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // For development convenience
public class InterviewController {

    private final SkillBridgeService skillBridgeService;

    @Autowired
    public InterviewController(SkillBridgeService skillBridgeService) {
        this.skillBridgeService = skillBridgeService;
    }

    @PostMapping("/generate-interview")
    public ResponseEntity<InterviewGenerationResponseDTO> generateInterview(
            @RequestBody InterviewGenerationRequestDTO request) {
        if (request.getJobRole() == null || request.getJobRole().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Handle empty arrays gracefully in service if needed,
        // but here we just pass them along.

        try {
            InterviewGenerationResponseDTO response = skillBridgeService.generateInterviewQuestions(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
