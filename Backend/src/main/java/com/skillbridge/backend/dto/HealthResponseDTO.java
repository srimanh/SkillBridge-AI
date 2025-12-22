package com.skillbridge.backend.dto;

public class HealthResponseDTO {
    private String status;
    private String service;

    public HealthResponseDTO() {}

    public HealthResponseDTO(String status, String service) {
        this.status = status;
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
