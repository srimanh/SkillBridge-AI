package com.skillbridge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletRewriteResponseDTO {
    private String rewrittenBullet;
    private String whyThisIsBetter;
}
