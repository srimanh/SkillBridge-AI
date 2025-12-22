package com.skillbridge.backend.controller;

import com.skillbridge.backend.dto.HealthResponseDTO;
import com.skillbridge.backend.service.SkillBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    private final SkillBridgeService skillBridgeService;

    @Autowired
    public HealthController(SkillBridgeService skillBridgeService) {
        this.skillBridgeService = skillBridgeService;
    }

    @GetMapping("/health")
    public HealthResponseDTO checkHealth() {
        return skillBridgeService.getHealthStatus();
    }
}
