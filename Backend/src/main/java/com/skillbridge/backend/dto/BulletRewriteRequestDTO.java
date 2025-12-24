package com.skillbridge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletRewriteRequestDTO {
    private String originalBullet;
    private String targetRole;
    private String focusSkill;
}
