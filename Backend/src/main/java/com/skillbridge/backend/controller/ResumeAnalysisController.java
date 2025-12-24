package com.skillbridge.backend.controller;

import com.skillbridge.backend.dto.ResumeAnalysisRequestDTO;
import com.skillbridge.backend.dto.ResumeAnalysisResponseDTO;
import com.skillbridge.backend.service.SkillBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ResumeAnalysisController {

    private final SkillBridgeService skillBridgeService;

    @Autowired
    public ResumeAnalysisController(SkillBridgeService skillBridgeService) {
        this.skillBridgeService = skillBridgeService;
    }

    @PostMapping("/analyze-resume")
    public ResumeAnalysisResponseDTO analyzeResume(@RequestBody ResumeAnalysisRequestDTO request) {
        return skillBridgeService.analyzeResume(request);
    }

    @PostMapping("/rewrite-bullet")
    public com.skillbridge.backend.dto.BulletRewriteResponseDTO rewriteBullet(
            @RequestBody com.skillbridge.backend.dto.BulletRewriteRequestDTO request) {
        return skillBridgeService.rewriteBullet(request);
    }
}
